package com.leyou.common.service.pojo.dto.application;

import lombok.Data;

/**
 * 保存品类
 *
 * @author shaoyijiong
 * @date 2018/12/25
 */
@Data
public class SaveCategoryDto {

  private String name;
  private Boolean isParent;
  private Long parentId;
  private Integer sort;
}
