package com.leyou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.base.response.PageResult;
import com.leyou.controller.req.BrandQueryPageReq;
import com.leyou.item.pojo.domain.Brand;
import com.leyou.query.BrandQuery;
import com.leyou.query.dto.BrandQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品类目.
 *
 * @author shaoyijong
 * @date 2018/11/28
 */
@RestController
@RequestMapping("brand")
public class BrandController {

  private final BrandQuery query;

  @Autowired
  public BrandController(BrandQuery query) {
    this.query = query;
  }

  @GetMapping("page")
  public ResponseEntity<IPage> queryBrandByPage(BrandQueryPageReq req) {
    return ResponseEntity.ok(query.queryBrandByPage(req, req.getKey()));
  }
}
