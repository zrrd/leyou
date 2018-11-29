package com.leyou.item.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 品牌.
 *
 * @author 邵益炯
 * @date 2018/11/29
 */
@Getter
@Setter
@TableName("tb_brand")
public class Brand {

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
}
