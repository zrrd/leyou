package com.leyou.common.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 商品类目表.
 *
 * @author shaoyijong
 * @date 2018/11/28
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_category")
public class Category extends Model<Category> {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  private String name;
  private Long parentId;
  private Boolean isParent;
  private Integer sort;

  public static Category newIntsForSave(String name, Long parentId, Boolean isParent,
      Integer sort) {
    return new Category(null, name, parentId, isParent, sort);
  }

  /**
   * 静态方法 为了编辑
   */
  public static Category newIntsForEdit(Long id, String name) {
    Category category = new Category();
    category.setId(id);
    category.setName(name);
    return category;
  }

  /**
   * 静态方法 为了删除
   */
  public static Category newIntsForDelete(Long id) {
    Category category = new Category();
    category.setId(id);
    return category;
  }

  public Category(Long id) {
    this.id = id;
  }

  public void findParent(Long id) {
    this.id = id;
  }
}
