package com.leyou.service.controller;

import com.leyou.service.application.CategoryApplication;
import com.leyou.service.application.dto.SaveCategoryDto;
import com.leyou.service.controller.req.EditCategoryReq;
import com.leyou.service.controller.req.SaveCategoryReq;
import com.leyou.service.query.CategoryQuery;
import com.leyou.service.query.dto.CategoryQueryDto;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  private final CategoryApplication application;

  private final CategoryQuery query;

  @Autowired
  public CategoryController(CategoryQuery query, CategoryApplication application) {
    this.query = query;
    this.application = application;
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

  /**
   * 保存商品品类
   */
  @PostMapping
  public ResponseEntity<Void> saveCategory(@RequestBody SaveCategoryReq req) {
    SaveCategoryDto dto = new SaveCategoryDto();
    BeanUtils.copyProperties(req, dto);
    application.saveCategory(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("{id}")
  public ResponseEntity<Void> editCategory(@PathVariable Long id,
      @RequestBody EditCategoryReq req) {
    application.editCategory(id, req.getName());
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    application.deleteCategory(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("bid/{bid}")
  public ResponseEntity<List<CategoryQueryDto>> queryByBrandId(@PathVariable("bid") Long bid) {
    List<CategoryQueryDto> list = query.queryCategoryByBid(bid);
    if (list == null || list.size() < 1) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(list);
  }

}
