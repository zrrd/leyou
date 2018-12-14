package com.leyou.service.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leyou.common.base.response.PageResult;
import com.leyou.service.controller.req.SaveGoodsReq;
import com.leyou.service.controller.req.SpuQueryPageReq;
import com.leyou.service.query.GoodsQuery;
import com.leyou.service.query.dto.SpuQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @Autowired
  public GoodsController(GoodsQuery goodsQuery) {
    this.goodsQuery = goodsQuery;
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

  @PostMapping("goods")
  public ResponseEntity<Void> saveGoods(@RequestBody SaveGoodsReq req) {
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
