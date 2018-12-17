package com.leyou.service.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leyou.common.base.response.PageResult;
import com.leyou.service.application.SkuApplication;
import com.leyou.service.application.SpuApplication;
import com.leyou.service.application.SpuDetailApplication;
import com.leyou.service.application.StockApplication;
import com.leyou.service.application.dto.SaveSkuDto;
import com.leyou.service.application.dto.SaveSpuDetailDto;
import com.leyou.service.application.dto.SaveSpuDto;
import com.leyou.service.controller.req.SaveGoodsReq;
import com.leyou.service.controller.req.SaveGoodsReq.SkusBean;
import com.leyou.service.controller.req.SpuQueryPageReq;
import com.leyou.service.query.GoodsQuery;
import com.leyou.service.query.dto.SpuQueryDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品接口.
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

  @SuppressWarnings("CheckStyle")
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
  public ResponseEntity<PageResult> querySpuByPage(SpuQueryPageReq req) {
    IPage<SpuQueryDto> page = goodsQuery
        .querySpuByPage(req.getPage(), req.getKey(), req.getSaleable());
    return ResponseEntity.ok(PageResult.getPageResult(page));
  }

  /**
   * 保存商品信息.
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

    for (SkusBean sku : req.getSkus()) {
      //保存商品实体信息
      SaveSkuDto saveSkuDto = new SaveSkuDto(spuId);
      BeanUtils.copyProperties(sku, saveSkuDto);
      Long skuId = skuApplication.saveSku(saveSkuDto);
      //保存库存信息
      stockApplication.saveStock(skuId, sku.getStock());
    }

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
