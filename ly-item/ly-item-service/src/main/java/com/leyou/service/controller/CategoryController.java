package com.leyou.service.controller;

import com.leyou.service.query.CategoryQuery;
import com.leyou.service.query.dto.CategoryQueryDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("category")
public class CategoryController {

  private final CategoryQuery query;

  @Autowired
  public CategoryController(CategoryQuery query) {
    this.query = query;
  }

  /**
   * 根据父类商品类目查找子类商品类目.
   *
   * @param pid 父类商品id
   * @return 子类商品列表
   */
  @GetMapping("list")
  public ResponseEntity<List<CategoryQueryDto>> queryCategoryByPid(Long pid) {
    List<CategoryQueryDto> list = query.queryCategoryByPid(pid);
    if (list.isEmpty()) {
      // 没找到 返回404
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    // 找到返回 200
    return ResponseEntity.ok(list);
  }

}
