package com.leyou.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.leyou.common.service.pojo.domain.Spu;
import com.leyou.common.service.pojo.dto.query.SkuQueryDto;
import com.leyou.common.service.pojo.dto.query.SpuDetailEditQueryDto;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.Specification;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 查找服务
 *
 * @author shaoyijiong
 * @date 2018/12/27
 */
@Service
public class SearchService {

  private final BrandClient brandClient;
  private final CategoryClient categoryClient;
  private final GoodsClient goodsClient;
  private final SpecClient specClient;

  private ObjectMapper mapper = new ObjectMapper();

  /**
   * 注入
   */
  @Autowired
  public SearchService(BrandClient brandClient, CategoryClient categoryClient,
      GoodsClient goodsClient, SpecClient specClient) {
    this.brandClient = brandClient;
    this.categoryClient = categoryClient;
    this.goodsClient = goodsClient;
    this.specClient = specClient;
  }

  @SuppressWarnings("CheckStyle")
  public  Goods buildGoods(Long id)  {
    Spu spu = goodsClient.querySpuById(id);

    StringBuilder all = new StringBuilder();
    all.append(spu.getTitle());

    //查询商品分类名称
    List<String> categoryNames = categoryClient
        .queryNameByIds(ImmutableList.of(spu.getCid1(), spu.getCid2(), spu.getCid3()));

    Joiner joiner = Joiner.on(" ").skipNulls();
    all.append(joiner.join(categoryNames));

    String brandName = brandClient.queryBrandName(id);
    all.append(brandName);

    //查询sku
    List<SkuQueryDto> skuList = goodsClient.querySkuBySpuId(id);
    //价格
    Set<Long> priceSet = Sets.newHashSet();

    //处理sku 只放我想要的数据
    List<Map> skuData = Lists.newArrayListWithCapacity(skuList.size());
    for (SkuQueryDto sku : skuList) {
      Map<String, Object> map = Maps.newHashMapWithExpectedSize(4);
      map.put("id", sku.getId());
      map.put("title", sku.getTitle());
      map.put("image", StringUtils.isBlank(sku.getImages()) ? "" : sku.getImages().split(",")[0]);
      map.put("price", sku.getPrice());
      priceSet.add(sku.getPrice());
      skuData.add(map);
    }

    //查询spuDetail
    SpuDetailEditQueryDto spuDetail = goodsClient.querySpuDetailById(id);

    //查询规格参数  每个个体的规格参数
    String specJsonForSpu = spuDetail.getSpecifications();
    List<Specification> maps = null;
    try {
      maps = mapper
          .readValue(specJsonForSpu, new TypeReference<List<Specification>>() {
          });
    } catch (IOException e) {
      e.printStackTrace();
    }

    //转换规格参数
    Map<String, Object> specMap = Maps.newHashMap();
    Consumer<Map<String, Object>> consumer = a -> {
      if (a.get("v") != null) {
        specMap.put((String) a.get("k"), a.get("v"));
      } else if (a.get("options") != null) {
        specMap.put((String) a.get("k"), a.get("options"));
      }
    };
    assert maps != null;
    maps.stream().map(Specification::getParams).flatMap(List::stream)
        .filter(a -> (Boolean) a.get("searchable")).forEach(consumer);

    Goods goods = new Goods();
    goods.setId(spu.getId());
    goods.setBrandId(spu.getBrandId());
    goods.setCid1(spu.getCid1());
    goods.setCid2(spu.getCid2());
    goods.setCid3(spu.getCid3());
    goods.setCreateTime(spu.getCreateTime());
    goods.setSubTitle(spu.getSubTitle());
    goods.setAll(all.toString());
    goods.setPrice(priceSet);
    try {
      goods.setSkus(mapper.writeValueAsString(skuData));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    goods.setSpecs(specMap);
    return goods;
  }

}
