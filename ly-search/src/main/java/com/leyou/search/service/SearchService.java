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
import com.leyou.common.base.response.PageResult;
import com.leyou.common.service.pojo.domain.Brand;
import com.leyou.common.service.pojo.domain.Category;
import com.leyou.common.service.pojo.dto.query.SkuQueryDto;
import com.leyou.common.service.pojo.dto.query.SpuDetailEditQueryDto;
import com.leyou.common.service.pojo.dto.query.SpuDto;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecClient;
import com.leyou.search.controller.req.SearchPageReq;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.Specification;
import com.leyou.search.repository.GoodsRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * 查找服务
 *
 * @author shaoyijiong
 * @date 2018/12/27
 */
@Service
@Slf4j
public class SearchService {

  private final GoodsRepository goodsRepository;

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
      GoodsClient goodsClient, SpecClient specClient, GoodsRepository goodsRepository) {
    this.brandClient = brandClient;
    this.categoryClient = categoryClient;
    this.goodsClient = goodsClient;
    this.specClient = specClient;
    this.goodsRepository = goodsRepository;
  }

  /**
   * 构建要插入es 的商品信息
   */
  public Goods buildGoods(Long id) {
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

  /**
   * 搜索
   */
  public PageResult<Goods> search(SearchPageReq req) {
    // 判断是否有搜索条件，如果没有直接返回null，不允许搜索全部商品
    if (Strings.isNullOrEmpty(req.getKey())) {
      return null;
    }

    //1. 构造查询条件
    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    //1.1 基本查询
    MatchQueryBuilder basicQuery = QueryBuilders.matchQuery("all", req.getKey())
        .operator(Operator.AND);
    queryBuilder.withQuery(basicQuery);
    // 通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
    queryBuilder
        .withSourceFilter(new FetchSourceFilter(new String[]{"id", "skus", "subTitle"}, null));

    //1.2 分页排序
    searchWithPageAndSort(queryBuilder, req);

    // 聚合
    queryBuilder.addAggregation(AggregationBuilders.terms("brands").field("cid3"));
    queryBuilder.addAggregation(AggregationBuilders.terms("category").field("brandId"));

    // 执行查询获取结果集
    AggregatedPage<Goods> goods = (AggregatedPage<Goods>) this.goodsRepository
        .search(queryBuilder.build());

    // 获取聚合结果集
    // 商品分类的聚合结果
    List<Category> categories =
        getCategoryAggResult(goods.getAggregation("brands"));
    // 品牌的聚合结果
    List<Brand> brands = getBrandAggResult(goods.getAggregation("category"));

    int totalPages = goods.getTotalPages();
    Long total = goods.getTotalElements();
    List<Goods> goodsList = goods.getContent();
    return new PageResult<>(total, (long) totalPages, goodsList);
  }


  private void searchWithPageAndSort(NativeSearchQueryBuilder queryBuilder, SearchPageReq req) {
    // es 分页从第0页开始
    int page = req.getPage() - 1;
    int size = SearchPageReq.DEFAULT_ROWS;

    //1.分页
    queryBuilder.withPageable(PageRequest.of(page, size));

    //2. 排序
    String sortBy = req.getSortBy();
    Boolean desc = req.getDesc();
    if (!Strings.isNullOrEmpty(sortBy)) {
      queryBuilder
          .withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
    }
  }

  /**
   * 解析品牌聚合结果
   */
  private List<Brand> getBrandAggResult(Aggregation aggregation) {
    try {
      LongTerms brandAgg = (LongTerms) aggregation;
      List<Long> bids = Lists.newArrayList();
      for (LongTerms.Bucket bucket : brandAgg.getBuckets()) {
        bids.add(bucket.getKeyAsNumber().longValue());
      }
      // 根据id查询品牌
      return this.brandClient.queryBrandByIds(bids);
    } catch (Exception e) {
      log.error("品牌聚合出现异常：", e);
      return null;
    }
  }

  /**
   * 解析商品分类聚合结果
   **/
  private List<Category> getCategoryAggResult(Aggregation aggregation) {
    try {
      List<Category> categories = Lists.newArrayList();
      LongTerms categoryAgg = (LongTerms) aggregation;
      List<Long> cids = Lists.newArrayList();
      for (LongTerms.Bucket bucket : categoryAgg.getBuckets()) {
        cids.add(bucket.getKeyAsNumber().longValue());
      }
      // 根据id查询分类名称
      List<String> names = this.categoryClient.queryNameByIds(cids);

      for (int i = 0; i < names.size(); i++) {
        Category c = Category.newIntsForEdit(cids.get(i), names.get(i));
        categories.add(c);
      }
      return categories;
    } catch (Exception e) {
      log.error("分类聚合出现异常：", e);
      return null;
    }
  }

  /**
   * 聚合出规格参数
   */
  private List<Map<String, Object>> getSpec(Long cid, QueryBuilder query) {
    try {
      // 不管是全局参数还是sku参数，只要是搜索参数，都根据分类id查询出来
      List<SpecParam> params = specClient.querySpecParam(null, cid, true, null);
      List<Map<String, Object>> specs = new ArrayList<>();

      NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
      queryBuilder.withQuery(query);

      // 聚合规格参数
      params.forEach(p -> {
        String key = p.getName();
        queryBuilder
            .addAggregation(AggregationBuilders.terms(key).field("specs." + key + ".keyword"));

      });

      // 查询
      Map<String, Aggregation> aggs = this.elasticsearchTemplate.query(queryBuilder.build(),
          SearchResponse::getAggregations).asMap();

      // 解析聚合结果
      params.forEach(param -> {
        Map<String, Object> spec = new HashMap<>();
        String key = param.getName();
        spec.put("k", key);
        StringTerms terms = (StringTerms) aggs.get(key);
        spec.put("options", terms.getBuckets().stream().map(StringTerms.Bucket::getKeyAsString));
        specs.add(spec);
      });

      return specs;
    } catch (
        Exception e) {
      logger.error("规格聚合出现异常：", e);
      return null;
    }

  }


}
