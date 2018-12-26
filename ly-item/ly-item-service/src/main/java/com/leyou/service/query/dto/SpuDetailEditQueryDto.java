package com.leyou.service.query.dto;

import lombok.Data;

/**
 * @author shaoyijiong
 * @date 2018/12/26
 */
@Data
public class SpuDetailEditQueryDto {

  private Long spuId;
  private String packingList;
  private String afterService;
  private String description;
  private String specTemplate;
  private String specifications;
}
