package com.leyou.goods.service;

import com.google.common.collect.ImmutableList;
import com.leyou.goods.client.BrandClient;
import com.leyou.goods.client.CategoryClient;
import com.leyou.goods.client.GoodsClient;
import com.leyou.service.pojo.domain.Brand;
import com.leyou.service.pojo.domain.Category;
import com.leyou.service.pojo.dto.query.SkuQueryDto;
import com.leyou.service.pojo.dto.query.SpuDetailEditQueryDto;
import com.leyou.service.pojo.dto.query.SpuDto;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author shaoyijiong
 * @date 2019/1/7
 */
@Slf4j
@Service
public class GoodsService {

  @Value("${static.html.url}")
  private String htmlUrl;
  private final TemplateEngine templateEngine;
  private final GoodsClient goodsClient;
  private final BrandClient brandClient;
  private final CategoryClient categoryClient;

  /**
   * 注入
   */
  @Autowired
  public GoodsService(GoodsClient goodsClient, BrandClient brandClient,
      CategoryClient categoryClient, TemplateEngine templateEngine) {
    this.goodsClient = goodsClient;
    this.brandClient = brandClient;
    this.categoryClient = categoryClient;
    this.templateEngine = templateEngine;
  }

  /**
   * 加载属性
   */
  public Map<String, Object> loadModel(Long spuId) {
    SpuDto spu = goodsClient.querySpuById(spuId);
    SpuDetailEditQueryDto spuDetail = goodsClient.querySpuDetailById(spuId);
    List<SkuQueryDto> skus = goodsClient.querySkuBySpuId(spuId);
    Brand brand = brandClient.queryBrandByIds(ImmutableList.of(spu.getBrandId())).get(0);
    List<Category> categories = getCategories(spu);
    HashMap<String, Object> map = new HashMap<>(5);
    map.put("spu", spu);
    map.put("spuDetail", spuDetail);
    map.put("skus", skus);
    map.put("categories", categories);
    map.put("brand", brand);
    return map;
  }

  private List<Category> getCategories(SpuDto spu) {
    try {
      List<String> names = this.categoryClient.queryNameByIds(
          Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
      Category c1 = Category.newIntsForEdit(spu.getCid1(), names.get(0));
      Category c2 = Category.newIntsForEdit(spu.getCid2(), names.get(1));
      Category c3 = Category.newIntsForEdit(spu.getCid3(), names.get(2));
      return Arrays.asList(c1, c2, c3);
    } catch (Exception e) {
      log.error("查询商品分类出错，spuId：{}", spu.getId(), e);
    }
    return null;
  }

  /**
   * 异步调用生成html任务
   */
  @Async
  public void createHtml(Map<String, Object> map, Long spuId) {

    if (map == null) {
      map = loadModel(spuId);
    }

    //上下文
    Context context = new Context();
    context.setVariables(map);
    //输出流
    File file = new File(htmlUrl, spuId + ".html");
    //如果存在的话先删除
    file.deleteOnExit();
    //tyr with resource 自动关闭流
    try (FileWriter writer = new FileWriter(file, false)) {
      //生成html
      templateEngine.process("item", context, writer);
    } catch (IOException e) {
      log.error("生成静态页异常", e);
    }
  }

  /**
   * 删除文件
   */
  @Async
  public void delete(Long spuId) {

    //输出流
    File file = new File(htmlUrl, spuId + ".html");
    file.deleteOnExit();
  }
}
