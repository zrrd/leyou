package com.leyou.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品类目表.
 *
 * @author shaoyijong
 * @date 2018/11/28
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@TableName("tb_category")
public class Category extends Model<Category> {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  private String name;
  private Long parentId;
  private Boolean isParent;
  private Integer sort;
}
