package com.leyou.service.pojo.dto.query;

import lombok.Data;

/**
 * 商品类目表.
 *
 * @author shaoyijong
 * @date 2018/11/28
 */
@Data
public class CategoryQueryDto {

  private Long id;
  private String name;
  private Long parentId;
  private Boolean isParent;
  private Integer sort;
}
