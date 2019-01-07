package com.leyou.common.service.controller;

import com.leyou.common.service.application.CategoryApplication;
import com.leyou.common.service.mvc.req.EditCategoryReq;
import com.leyou.common.service.mvc.req.SaveCategoryReq;
import com.leyou.common.service.pojo.domain.Category;
import com.leyou.common.service.pojo.dto.application.SaveCategoryDto;
import com.leyou.common.service.pojo.dto.query.CategoryQueryDto;
import com.leyou.common.service.query.CategoryQuery;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品类目
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
   */
  @GetMapping("list")
  public ResponseEntity<List<CategoryQueryDto>> queryCategoryByPid(Long pid) {
    List<CategoryQueryDto> list = query.queryCategoryByPid(pid);
    if (list.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
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

  /**
   * 修改
   */
  @PutMapping("{id}")
  public ResponseEntity<Void> editCategory(@PathVariable Long id,
      @RequestBody EditCategoryReq req) {
    application.editCategory(id, req.getName());
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  /**
   * 删除
   */
  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    application.deleteCategory(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  /**
   * 根据商品id查找品类
   */
  @GetMapping("bid/{bid}")
  public ResponseEntity<List<CategoryQueryDto>> queryByBrandId(@PathVariable("bid") Long bid) {
    List<CategoryQueryDto> list = query.queryCategoryByBid(bid);
    if (list == null || list.size() < 1) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(list);
  }

  /**
   * 根据类目id查找类目名
   */
  @GetMapping("names")
  public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids") List<Long> ids) {
    List<String> list = query.queryCategoryName(ids);
    if (list == null || list.size() < 1) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(list);
  }

  /**
   * 根据3级分类id，查询1~3级的分类
   */
  @GetMapping("all/level")
  public ResponseEntity<List<Category>> queryAllByCid3(@RequestParam("id") Long id) {
    List<Category> list = query.queryAllByCid3(id);
    if (list == null || list.size() < 1) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(list);
  }
}
