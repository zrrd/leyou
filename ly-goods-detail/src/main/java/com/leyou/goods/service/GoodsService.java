package com.leyou.goods.service;

import com.google.common.collect.ImmutableList;
import com.leyou.common.service.pojo.domain.Brand;
import com.leyou.common.service.pojo.domain.Category;
import com.leyou.common.service.pojo.dto.query.SkuQueryDto;
import com.leyou.common.service.pojo.dto.query.SpuDetailEditQueryDto;
import com.leyou.common.service.pojo.dto.query.SpuDto;
import com.leyou.goods.client.BrandClient;
import com.leyou.goods.client.CategoryClient;
import com.leyou.goods.client.GoodsClient;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shaoyijiong
 * @date 2019/1/7
 */
@SuppressWarnings("CheckStyle")
@Slf4j
@Service
public class GoodsService {

  private final GoodsClient goodsClient;
  private final BrandClient brandClient;
  private final CategoryClient categoryClient;

  @Autowired
  public GoodsService(GoodsClient goodsClient, BrandClient brandClient,
      CategoryClient categoryClient) {
    this.goodsClient = goodsClient;
    this.brandClient = brandClient;
    this.categoryClient = categoryClient;
  }

  public Map<String, Object> loadModel(Long spuId) {
    SpuDto spu = goodsClient.querySpuById(spuId);
    SpuDetailEditQueryDto spuDetail = goodsClient.querySpuDetailById(spuId);
    List<SkuQueryDto> skus = goodsClient.querySkuBySpuId(spuId);
    Brand brand = brandClient.queryBrandByIds(ImmutableList.of(spu.getBrandId())).get(0);
    List<Category> categories = getCategories(spu);
    HashMap<String, Object> map = new HashMap<>();
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
}