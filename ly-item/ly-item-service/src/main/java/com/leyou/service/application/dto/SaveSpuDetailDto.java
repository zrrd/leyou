package com.leyou.service.application.dto;

import lombok.Data;

/**
 * .
 *
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Data
public class SaveSpuDetailDto {

  Long spuId;
  String description;
  String specifications;
  String specTemplate;
  String packingList;
  String afterService;

  public SaveSpuDetailDto(Long spuId) {
    this.spuId = spuId;
  }
}
