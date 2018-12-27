package com.leyou.common.service.pojo.dto.query;

import lombok.Data;

/**
 * @author shaoyijiong
 * @date 2018/12/26
 */
@Data
public class SkuQueryDto {

  private Long id;
  private Boolean enable;
  private String title;
  private String images;
  private Long price;
  private String ownSpec;
  private String indexes;
  private Integer stock;
}
