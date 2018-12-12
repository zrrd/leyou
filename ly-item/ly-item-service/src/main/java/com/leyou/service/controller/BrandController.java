package com.leyou.service.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Iterables;
import com.leyou.common.base.response.PageResult;
import com.leyou.service.application.BrandApplication;
import com.leyou.service.application.dto.SaveBrandDto;
import com.leyou.service.controller.req.BrandQueryPageReq;
import com.leyou.service.controller.req.SaveBrandReq;
import com.leyou.service.query.BrandQuery;
import com.leyou.service.query.dto.BrandQueryDto;
import com.leyou.service.query.dto.SelectBrandDto;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final BrandApplication application;

  @Autowired
  public BrandController(BrandQuery query, BrandApplication application) {
    this.query = query;
    this.application = application;
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

  /**
   * 新增品牌.
   */
  @PostMapping("save")
  public ResponseEntity<Void> saveBrand(SaveBrandReq req) {
    SaveBrandDto dto = new SaveBrandDto(req.getName(), req.getImage(), req.getLetter());
    application.saveBrand(dto, req.getCategories());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 根据类别查询.
   */
  @GetMapping("cid/{id}")
  public ResponseEntity queryBrandByCategory(@PathVariable Long id) {
    List<SelectBrandDto> list = query.queryByCategoryId(id);
    if (Iterables.isEmpty(list)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(list);
  }

}
