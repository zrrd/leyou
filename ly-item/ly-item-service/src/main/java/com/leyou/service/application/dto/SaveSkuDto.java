package com.leyou.service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * .
 *
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Data
public class SaveSkuDto {

  Long spuId;
  Boolean enable;
  Long price;
  String images;
  String indexes;
  String ownSpec;
  String title;

  public SaveSkuDto(Long spuId) {
    this.spuId = spuId;
  }
}
