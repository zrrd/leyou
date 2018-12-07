package com.leyou.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 品牌.
 *
 * @author 邵益炯
 * @date 2018/11/29
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@TableName("tb_brand")
public class Brand extends Model<Brand> {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  /**
   * 品牌名称.
   */
  private String name;
  /**
   * 品牌图片.
   */
  private String image;
  /**
   * 品牌首字母.
   */
  private Character letter;


  /**
   * 品牌私有构造类.
   */
  private Brand() {

  }

  /**
   * 全参构造.
   */
  @SuppressWarnings("WeakerAccess")
  public Brand(Long id, String name, String image, Character letter) {
    this.id = id;
    this.name = name;
    this.image = image;
    this.letter = letter;
  }

  /**
   * 构建品牌.
   */
  public static Brand newInstForSave(String name, String image, Character letter) {
    return new Brand(null, name, image, letter);
  }

  /**
   * 保存品牌.
   */
  public void saveBrand() {
    checkBrandName();
    insert();
  }

  /**
   * 校验品牌名是否被占用.
   */
  private void checkBrandName() {
    QueryWrapper<Brand> qw = new QueryWrapper<>();
    qw.eq("name", name);
    Integer num = selectCount(qw);
    if (num > 0) {
      throw new RuntimeException("该品牌名称已被使用,请使用其他品牌名称");
    }
  }
}
