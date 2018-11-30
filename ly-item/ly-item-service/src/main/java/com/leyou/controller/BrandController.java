package com.leyou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Iterables;
import com.leyou.base.response.PageResult;
import com.leyou.controller.req.BrandQueryPageReq;
import com.leyou.controller.req.SaveBeandReq;
import com.leyou.item.pojo.domain.Brand;
import com.leyou.query.BrandQuery;
import com.leyou.query.dto.BrandQueryDto;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

  /**
   * 分页查询品牌.
   */
  @GetMapping("page")
  public ResponseEntity<PageResult> queryBrandByPage(BrandQueryPageReq req) {
    IPage<BrandQueryDto> page = query.queryBrandByPage(req.getPage(), req.getKey());
    if (Iterables.isEmpty(page.getRecords())) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.ok(PageResult.getPageResult(page));
  }

  @PostMapping
  public ResponseEntity<Void> saveBrand(SaveBeandReq req) {
    return ResponseEntity.ok().body(null);
  }
}
