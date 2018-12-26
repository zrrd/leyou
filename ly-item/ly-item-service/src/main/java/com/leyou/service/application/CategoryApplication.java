package com.leyou.service.application;

import com.leyou.service.pojo.domain.Category;
import com.leyou.service.pojo.dto.application.SaveCategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品操作
 *
 * @author shaoyijiong
 * @date 2018/12/25
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class CategoryApplication {

  /**
   * 保存品类
   */
  public void saveCategory(SaveCategoryDto dto) {
    Category category = Category
        .newIntsForSave(dto.getName(), dto.getParentId(), dto.getIsParent(), dto.getSort());
    category.insert();
  }

  /**
   * 修改品牌名
   */
  public void editCategory(Long id, String name) {
    Category category = Category.newIntsForEdit(id, name);
    category.updateById();
  }

  public void deleteCategory(Long id) {
    Category category = Category.newIntsForDelete(id);
    category.deleteById();
  }
}
