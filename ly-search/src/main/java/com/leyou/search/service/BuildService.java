package com.leyou.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.Specification;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.service.pojo.dto.query.SkuQueryDto;
import com.leyou.service.pojo.dto.query.SpuDetailEditQueryDto;
import com.leyou.service.pojo.dto.query.SpuDto;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 构建数据操作
 *
 * @author shaoyijiong
 * @date 2019/1/4
 */
@Slf4j
@Service
public class BuildService {

  private final GoodsClient goodsClient;
  private final BrandClient brandClient;
  private final CategoryClient categoryClient;
  private ObjectMapper mapper = new ObjectMapper();
  private final GoodsRepository goodsRepository;

  @SuppressWarnings("CheckStyle")
  @Autowired
  public BuildService(CategoryClient categoryClient, GoodsClient goodsClient,
      BrandClient brandClient, GoodsRepository goodsRepository) {
    this.categoryClient = categoryClient;
    this.goodsClient = goodsClient;
    this.brandClient = brandClient;
    this.goodsRepository = goodsRepository;
  }


  /**
   * 构建要插入es 的商品信息
   */
  public Goods build(Long id) {
    SpuDto spu = goodsClient.querySpuById(id);

    //得到所有搜索字段
    String all = buildAll(spu.getTitle(),
        ImmutableList.of(spu.getCid1(), spu.getCid2(), spu.getCid3()),
        spu.getBrandId());

    //处理sku 与 价格
    Set<Long> priceSet = Sets.newHashSet();
    String sku = getSku(id, priceSet);

    //得到规格参数
    Goods goods = new Goods();
    goods.setId(spu.getId());
    goods.setBrandId(spu.getBrandId());
    goods.setCid1(spu.getCid1());
    goods.setCid2(spu.getCid2());
    goods.setCid3(spu.getCid3());
    goods.setCreateTime(spu.getCreateTime());
    goods.setSubTitle(spu.getSubTitle());
    goods.setAll(all);
    goods.setPrice(priceSet);
    goods.setSkus(sku);
    goods.setSpecs(getSpec(id));

    return goods;
  }

  /**
   * 处理sku
   */
  private String getSku(Long id, Set<Long> priceSet) {
    //查询sku
    List<SkuQueryDto> skuList = goodsClient.querySkuBySpuId(id);
    //处理sku 只放我想要的数据
    List<Map> skuData = Lists.newArrayListWithCapacity(skuList.size());
    skuList.forEach(sku -> {
      Map<String, Object> map = Maps.newHashMapWithExpectedSize(4);
      map.put("id", sku.getId());
      map.put("title", sku.getTitle());
      map.put("image", Strings.isNullOrEmpty(sku.getImages()) ? "" : sku.getImages().split(",")[0]);
      map.put("price", sku.getPrice());
      priceSet.add(sku.getPrice());
      skuData.add(map);
    });
    try {
      return mapper.writeValueAsString(skuData);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * 得到规格参数
   */
  private Map<String, Object> getSpec(Long id) {
    //查询spuDetail
    SpuDetailEditQueryDto spuDetail = goodsClient.querySpuDetailById(id);
    //查询规格参数  每个个体的规格参数
    String specJsonForSpu = spuDetail.getSpecifications();
    List<Specification> maps = null;
    try {
      maps = mapper.readValue(specJsonForSpu, new TypeReference<List<Specification>>() {
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
    //转换规格参数
    Map<String, Object> specMap = Maps.newHashMap();

    final String v = "v";
    final String k = "k";
    final String options = "options";
    Consumer<Map<String, Object>> consumer = a -> {
      if (a.get(v) != null) {
        specMap.put((String) a.get(k), a.get(v));
      } else if (a.get(options) != null) {
        specMap.put((String) a.get(k), a.get(options));
      }
    };
    assert maps != null;
    maps.stream().map(Specification::getParams).flatMap(List::stream)
        .filter(a -> (Boolean) a.get("searchable")).forEach(consumer);
    return specMap;
  }

  /**
   * 得到可搜索字段
   */
  private String buildAll(String title, List<Long> cidList, Long brandId) {
    StringBuilder all = new StringBuilder();
    //标题
    all.append(title);
    //查询商品分类名称
    List<String> categoryNames = categoryClient
        .queryNameByIds(cidList);
    Joiner joiner = Joiner.on(" ").skipNulls();
    all.append(joiner.join(categoryNames));
    //查询品牌名称
    String brandName = brandClient.queryBrandName(brandId);
    all.append(brandName);
    return all.toString();
  }

  public void insertIndex(List<Goods> goods) {
    goodsRepository.saveAll(goods);
  }

  public void deleteIndex(Long spuId) {
    goodsRepository.deleteById(spuId);
  }
}
