package com.leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.leyou.common.utils.NumberUtils;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.SpecClient;
import com.leyou.search.controller.req.SearchPageReq;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.pojo.Specification;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.service.pojo.domain.Brand;
import com.leyou.service.pojo.domain.Category;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.elasticsearch.search.aggregations.metrics.stats.InternalStats;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
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

  private final ElasticsearchTemplate template;
  private final GoodsRepository goodsRepository;
  private final BrandClient brandClient;
  private final CategoryClient categoryClient;
  private final SpecClient specClient;

  private ObjectMapper mapper = new ObjectMapper();

  /**
   * 注入
   */
  @Autowired
  public SearchService(BrandClient brandClient, CategoryClient categoryClient,
      SpecClient specClient, GoodsRepository goodsRepository,
      ElasticsearchTemplate template) {
    this.brandClient = brandClient;
    this.categoryClient = categoryClient;
    this.specClient = specClient;
    this.goodsRepository = goodsRepository;
    this.template = template;
  }

  /**
   * 搜索
   */
  public SearchResult search(SearchPageReq req) {
    // 判断是否有搜索条件，如果没有直接返回null，不允许搜索全部商品
    if (Strings.isNullOrEmpty(req.getKey())) {
      return null;
    }

    //1. 构造查询条件
    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    //1.1 基本查询
    QueryBuilder basicQuery = buildBasicQueryWithFilter(req);
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
    AggregatedPage<Goods> goods = (AggregatedPage<Goods>) goodsRepository
        .search(queryBuilder.build());

    // 获取聚合结果集
    // 商品分类的聚合结果
    List<Category> categories =
        getCategoryAggResult(goods.getAggregation("brands"));
    // 品牌的聚合结果
    List<Brand> brands = getBrandAggResult(goods.getAggregation("category"));

    List<Map<String, Object>> specs = Lists.newArrayList();
    if (categories.size() == 1) {
      specs = getSpecifications(categories.get(0).getId(), basicQuery);
    }

    return new SearchResult(goods.getTotalElements(), (long) goods.getTotalPages(),
        goods.getContent(), categories, brands, specs);
  }

  /**
   * 构建基本查询条件
   */
  private QueryBuilder buildBasicQueryWithFilter(SearchPageReq request) {
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
    // 基本查询条件 搜索字段
    queryBuilder.must(QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND));
    // 过滤条件构建器
    BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
    // 整理过滤条件
    Map<String, String> filter = request.getFilter();
    for (Map.Entry<String, String> entry : filter.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      if (value.contains("-")) {
        //区间类型0-300
        String[] split = value.split("-");
        //查询的时候添加数字的上下边界
        filterQueryBuilder.must(
            QueryBuilders.rangeQuery("specs." + key).gt(Double.valueOf(split[0]))
                .lt(Double.valueOf(split[1])).includeUpper(true).includeLower(false));
      } else {
        // 商品分类和品牌要特殊处理
        if (!"cid3".equals(key) && !"brandId".equals(key)) {
          key = "specs." + key + ".keyword";
        }
        // 字符串类型，进行term查询
        filterQueryBuilder.must(QueryBuilders.termQuery(key, value));
      }


    }
    // 添加过滤条件
    queryBuilder.filter(filterQueryBuilder);
    return queryBuilder;
  }

  /**
   * 分页排序
   */
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
      //获取所有聚合中的品牌id
      List<Long> bids = brandAgg.getBuckets().stream().map(b -> b.getKeyAsNumber().longValue())
          .collect(Collectors.toList());
      // 根据id查询品牌
      return brandClient.queryBrandByIds(bids);
    } catch (Exception e) {
      log.error("品牌聚合出现异常：", e);
      return Lists.newArrayList();
    }
  }

  /**
   * 解析商品分类聚合结果
   **/
  private List<Category> getCategoryAggResult(Aggregation aggregation) {
    try {
      List<Category> categories = Lists.newArrayList();
      LongTerms categoryAgg = (LongTerms) aggregation;
      List<Long> cids = categoryAgg.getBuckets().stream().map(b -> b.getKeyAsNumber().longValue())
          .collect(Collectors.toList());
      // 根据id查询分类名称 组装分类
      List<String> names = categoryClient.queryNameByIds(cids);
      for (int i = 0; i < names.size(); i++) {
        Category c = Category.newIntsForEdit(cids.get(i), names.get(i));
        categories.add(c);
      }
      return categories;
    } catch (Exception e) {
      log.error("分类聚合出现异常：", e);
      return Lists.newArrayList();
    }
  }


  private List<Map<String, Object>> getSpecifications(Long id, QueryBuilder basicQuery) {
    // 1、根据分类查询规格
    String jsonSpec = specClient.querySpecifications(id);
    // 将规格反序列化为集合
    List<Specification> specs;
    try {
      specs = mapper.readValue(jsonSpec, new TypeReference<List<Specification>>() {
      });
    } catch (Exception e) {
      log.error("解析规格参数json出错，json={}", jsonSpec, e);
      return null;
    }

    // 2、过滤出可以搜索的哪些规格参数的名称，分成数值类型、字符串类型
    // 准备集合，保存字符串规格参数名
    Set<String> strSpec = Sets.newHashSet();
    // 准备map，保存数值规格参数名及单位
    Map<String, String> numericalUnits = Maps.newHashMap();
    // 解析规格
    for (Specification spec : specs) {
      List<Map<String, Object>> params = spec.getParams();
      for (Map<String, Object> param : params) {
        Boolean searchable = (Boolean) param.get("searchable");
        if (searchable) {
          // 判断是否是数值类型
          if (param.containsKey("numerical") && (boolean) param.get("numerical")) {
            numericalUnits.put(param.get("k").toString(), param.get("unit").toString());
          } else {
            strSpec.add(param.get("k").toString());
          }
        }
      }
    }

    // 3、聚合计算数值类型的interval
    Map<String, Double> numericalInterval = getNumericalInterval(id, numericalUnits.keySet());

    // 4、利用interval聚合计算数值类型的分段
    // 5、对字符串类型的参数进行聚合
    return aggForSpec(strSpec, numericalInterval, numericalUnits, basicQuery);
  }

  /**
   * 聚合得到interval
   **/
  private Map<String, Double> getNumericalInterval(Long cid, Set<String> keySet) {
    Map<String, Double> numbericalSpecs = Maps.newHashMap();
    // 准备查询条件
    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    // 不查询任何数据
    queryBuilder.withQuery(QueryBuilders.termQuery("cid3", cid.toString()))
        .withSourceFilter(new FetchSourceFilter(new String[]{""}, null))
        .withPageable(PageRequest.of(0, 1));
    // 添加stats类型的聚合
    for (String key : keySet) {
      queryBuilder.addAggregation(AggregationBuilders.stats(key).field("specs." + key));
    }
    Map<String, Aggregation> aggs = this.template.query(queryBuilder.build(),
        response -> response.getAggregations().asMap());

    for (String key : keySet) {
      InternalStats stats = (InternalStats) aggs.get(key);
      double interval = this.getInterval(stats.getMin(), stats.getMax(), stats.getSum());
      numbericalSpecs.put(key, interval);
    }
    return numbericalSpecs;
  }

  /**
   * 根据最小值，最大值，sum计算interval
   **/
  private double getInterval(double min, double max, Double sum) {
    double interval = (max - min) / 6.0d;
    // 判断是否是小数
    if (sum.intValue() == sum) {
      // 不是小数，要取整十、整百这样
      // 根据interval的整数位长度来判断位数
      int length = StringUtils.substringBefore(String.valueOf(interval), ".").length();
      double factor = Math.pow(10.0, length - 1);
      return Math.round(interval / factor) * factor;
    } else {
      // 是小数,我们只保留一位小数
      return NumberUtils.scale(interval, 1);
    }
  }

  /**
   * 根据规格参数，聚合得出过滤条件
   */
  private List<Map<String, Object>> aggForSpec(Set<String> strSpec,
      Map<String, Double> numericalInterval,
      Map<String, String> numericalUnits, QueryBuilder query) {
    List<Map<String, Object>> specs = new ArrayList<>();
    // 准备查询条件
    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    queryBuilder.withQuery(query);
    // 聚合数值类型
    for (Map.Entry<String, Double> entry : numericalInterval.entrySet()) {
      queryBuilder.addAggregation(
          AggregationBuilders.histogram(entry.getKey())
              .field("specs." + entry.getKey())
              .interval(entry.getValue())
              .minDocCount(1)
      );
    }
    // 聚合字符串
    for (String key : strSpec) {
      queryBuilder.addAggregation(
          AggregationBuilders.terms(key).field("specs." + key + ".keyword"));
    }

    // 解析聚合结果
    Map<String, Aggregation> aggs = this.template.query(
        queryBuilder.build(), SearchResponse::getAggregations).asMap();

    // 解析数值类型
    for (Map.Entry<String, Double> entry : numericalInterval.entrySet()) {
      Map<String, Object> spec = Maps.newHashMap();
      String key = entry.getKey();
      spec.put("k", key);
      spec.put("unit", numericalUnits.get(key));
      // 获取聚合结果
      InternalHistogram histogram = (InternalHistogram) aggs.get(key);
      spec.put("options", histogram.getBuckets().stream().map(bucket -> {
        Double begin = (double) bucket.getKey();
        Double end = begin + numericalInterval.get(key);
        // 对begin和end取整
        if (NumberUtils.isInt(begin) && NumberUtils.isInt(end)) {
          // 确实是整数，需要取整
          return begin.intValue() + "-" + end.intValue();
        } else {
          // 小数，取2位小数
          begin = NumberUtils.scale(begin, 2);
          end = NumberUtils.scale(end, 2);
          return begin + "-" + end;
        }
      }));
      specs.add(spec);
    }

    // 解析字符串类型
    strSpec.forEach(key -> {
      Map<String, Object> spec = Maps.newHashMap();
      spec.put("k", key);
      StringTerms terms = (StringTerms) aggs.get(key);
      spec.put("options", terms.getBuckets().stream().map(Bucket::getKeyAsString));
      specs.add(spec);
    });
    return specs;
  }
}
