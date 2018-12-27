package com.leyou.common.service.controller;

import com.google.common.base.Strings;
import com.leyou.common.service.application.SpecificationsApplication;
import com.leyou.common.service.mvc.req.SaveSpecReq;
import com.leyou.common.service.query.SpecificationsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 规格参数
 *
 * @author shaoyijiong
 * @date 2018/12/11
 */
@RestController
@RequestMapping("spec")
public class SpecificationsController {

  private final SpecificationsQuery query;

  private final SpecificationsApplication app;

  @Autowired
  public SpecificationsController(SpecificationsQuery query, SpecificationsApplication app) {
    this.query = query;
    this.app = app;
  }

  /**
   * 规格请求.
   */
  @GetMapping("{id}")
  public ResponseEntity<String> querySpecifications(@PathVariable("id") Long id) {
    String specifications = query.querySpecByCid(id);
    if (Strings.isNullOrEmpty(specifications)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(specifications);
  }

  /**
   * 保存规格参数.
   */
  @PostMapping
  public ResponseEntity saveSpecifications(SaveSpecReq req) {
    app.saveSpecifications(req.getCategoryId(), req.getSpecifications());
    return ResponseEntity.ok().build();
  }

  /**
   * 修改规格参数.
   */
  @PutMapping
  public ResponseEntity updateSpecifications(SaveSpecReq req) {
    app.updateSpecifications(req.getCategoryId(), req.getSpecifications());
    return ResponseEntity.ok().build();
  }

  @GetMapping("params")
  public ResponseEntity querySpecByCategoryId(Long cid) {
    return querySpecifications(cid);
  }

}
