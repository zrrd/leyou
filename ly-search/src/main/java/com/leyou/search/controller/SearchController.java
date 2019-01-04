package com.leyou.search.controller;

import com.google.common.collect.Iterables;
import com.leyou.common.base.response.PageResult;
import com.leyou.search.controller.req.SearchPageReq;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shaoyijiong
 * @date 2018/12/29
 */
@RestController
public class SearchController {

  private final SearchService searchService;

  @Autowired
  public SearchController(SearchService searchService) {
    this.searchService = searchService;
  }


  /**
   * 分页查找货物
   */
  @PostMapping("page")
  public ResponseEntity<SearchResult> page(@RequestBody SearchPageReq req) {
    SearchResult goods = searchService.search(req);
    if (Iterables.isEmpty(goods.getItems())) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(goods);
  }
}
