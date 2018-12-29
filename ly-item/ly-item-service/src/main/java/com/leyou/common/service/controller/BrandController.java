package com.leyou.common.service.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.leyou.common.base.response.PageResult;
import com.leyou.common.service.application.BrandApplication;
import com.leyou.common.service.mvc.req.BrandQueryPageReq;
import com.leyou.common.service.mvc.req.EditCategoryReq;
import com.leyou.common.service.mvc.req.SaveBrandReq;
import com.leyou.common.service.pojo.dto.application.EditBrandDto;
import com.leyou.common.service.pojo.dto.application.SaveBrandDto;
import com.leyou.common.service.pojo.dto.query.BrandQueryDto;
import com.leyou.common.service.pojo.dto.query.SelectBrandDto;
import com.leyou.common.service.query.BrandQuery;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 品牌
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
    IPage<BrandQueryDto> page = query.queryBrandByPage(req.getPages(), req.getKey());
    if (Iterables.isEmpty(page.getRecords())) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.ok(PageResult.getPageResult(page));
  }

  /**
   * 新增品牌.
   */
  @PostMapping
  public ResponseEntity<Void> saveBrand(SaveBrandReq req) {
    SaveBrandDto dto = new SaveBrandDto(req.getName(), req.getImage(), req.getLetter());
    application.saveBrand(dto, req.getCids());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 根据类别查询.
   */
  @GetMapping("cid/{id}")
  public ResponseEntity<List<SelectBrandDto>> queryBrandByCategory(@PathVariable Long id) {
    List<SelectBrandDto> list = query.queryByCategoryId(id);
    if (Iterables.isEmpty(list)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(list);
  }

  /**
   * 修改品牌
   */
  @PutMapping
  public ResponseEntity<Void> editBrand(EditCategoryReq req) {
    EditBrandDto editBrandDto = new EditBrandDto();
    BeanUtils.copyProperties(req, editBrandDto);
    application.editBrand(editBrandDto, req.getCids());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    application.deleteBrand(id);
    return ResponseEntity.ok().build();
  }

  /**
   * 根据ip获取品牌名称
   */
  @GetMapping("name")
  public ResponseEntity<String> queryBrandName(Long id) {
    String name = query.queryBrandName(id);
    if (Strings.isNullOrEmpty(name)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(name);
  }
}
