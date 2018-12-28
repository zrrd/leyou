package com.leyou.common.service.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.leyou.common.base.response.PageResult;
import com.leyou.common.service.application.SkuApplication;
import com.leyou.common.service.application.SpuApplication;
import com.leyou.common.service.application.SpuDetailApplication;
import com.leyou.common.service.application.StockApplication;
import com.leyou.common.service.mvc.req.SaveGoodsReq;
import com.leyou.common.service.mvc.req.SaveGoodsReq.SkusBean;
import com.leyou.common.service.mvc.req.SpuQueryPageReq;
import com.leyou.common.service.pojo.dto.application.SaveSkuDto;
import com.leyou.common.service.pojo.dto.application.SaveSpuDetailDto;
import com.leyou.common.service.pojo.dto.application.SaveSpuDto;
import com.leyou.common.service.pojo.dto.application.SaveStockDto;
import com.leyou.common.service.pojo.dto.query.SkuQueryDto;
import com.leyou.common.service.pojo.dto.query.SpuDetailEditQueryDto;
import com.leyou.common.service.pojo.dto.query.SpuDto;
import com.leyou.common.service.pojo.dto.query.SpuQueryDto;
import com.leyou.common.service.query.GoodsQuery;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品接口
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@RestController
public class GoodsController {

  private final GoodsQuery goodsQuery;
  private final SkuApplication skuApplication;
  private final SpuApplication spuApplication;
  private final SpuDetailApplication spuDetailApplication;
  private final StockApplication stockApplication;

  /**
   * 注入
   */
  @Autowired
  public GoodsController(GoodsQuery goodsQuery, SkuApplication skuApplication,
      SpuApplication spuApplication, SpuDetailApplication spuDetailApplication,
      StockApplication stockApplication) {
    this.goodsQuery = goodsQuery;
    this.skuApplication = skuApplication;
    this.spuApplication = spuApplication;
    this.spuDetailApplication = spuDetailApplication;
    this.stockApplication = stockApplication;
  }

  /**
   * 分页查询spu.
   */
  @GetMapping("spu/page")
  public ResponseEntity<PageResult<SpuQueryDto>> querySpuByPage(SpuQueryPageReq req) {
    IPage<SpuQueryDto> page = goodsQuery
        .querySpuByPage(req.getPage(), req.getKey(), req.getSaleable());
    return ResponseEntity.ok(PageResult.getPageResult(page));
  }


  /**
   * 保存商品信息
   */
  @PostMapping("goods")
  @Transactional(rollbackFor = Throwable.class)
  public ResponseEntity<Void> saveGoods(@RequestBody SaveGoodsReq req) {
    //保存抽象商品
    SaveSpuDto saveSpuDto = new SaveSpuDto();
    BeanUtils.copyProperties(req, saveSpuDto);
    Long spuId = spuApplication.saveSpu(saveSpuDto);
    //保存抽象商品描述
    SaveSpuDetailDto saveSpuDetailDto = new SaveSpuDetailDto(spuId);
    BeanUtils.copyProperties(req.getSpuDetail(), saveSpuDetailDto);
    spuDetailApplication.saveSpuDetail(saveSpuDetailDto);

    List<SaveStockDto> stockDtoList = Lists.newArrayList();
    for (SkusBean sku : req.getSkus()) {
      //保存商品实体信息
      SaveSkuDto saveSkuDto = new SaveSkuDto(spuId);
      BeanUtils.copyProperties(sku, saveSkuDto);
      Long skuId = skuApplication.saveSku(saveSkuDto);
      //保存库存信息
      SaveStockDto saveStockDto = new SaveStockDto(skuId, sku.getStock());
      stockDtoList.add(saveStockDto);
    }
    //批量存入数据库
    stockApplication.saveStock(stockDtoList);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 根据spuId 查找 spu详情
   */
  @GetMapping("spu/detail/{id}")
  public ResponseEntity<SpuDetailEditQueryDto> querySpuDetailById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(goodsQuery.querySpuDetail(id));
  }

  /**
   * 根据spuId 查找sku列表
   */
  @GetMapping("sku/list")
  public ResponseEntity<List<SkuQueryDto>> querySkuBySpuId(Long id) {
    return ResponseEntity.ok(goodsQuery.querySkuBySpuId(id));
  }

  /**
   * 根据id查询spu
   */
  @GetMapping("spu/{id}")
  public ResponseEntity<SpuDto> querySpuById(@PathVariable("id") Long id) {
    SpuDto spu = goodsQuery.querySpuById(id);
    if (spu == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(spu);
  }
}
