package com.leyou.service.controller;

import com.google.common.base.Strings;
import com.leyou.service.query.SpecificationsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @author shaoyijiong
 * @date 2018/12/11
 */
@RestController
@RequestMapping("spec")
public class SpecificationsController {

  private final SpecificationsQuery query;

  @Autowired
  public SpecificationsController(SpecificationsQuery query) {
    this.query = query;
  }

  /**
   * 规格请求.
   */
  @GetMapping("groups/{id}")
  public ResponseEntity<String> querySpecifications(@PathVariable("id") Long id) {
    String specifications = query.querySpecByCid(id);
    if (Strings.isNullOrEmpty(specifications)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(specifications);
  }

}
