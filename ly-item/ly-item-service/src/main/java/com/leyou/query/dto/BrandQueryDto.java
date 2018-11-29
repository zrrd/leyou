package com.leyou.query.dto;

import lombok.Data;

/**
 * 品牌查询对象.
 *
 * @author shoayijiong
 * @date 2018/11/29
 */
@Data
public class BrandQueryDto {

  private Long id;
  private String name;
  private String image;
  private Character letter;
}
