package com.leyou.service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * .
 *
 * @author shaoyijiong
 * @date 2018/12/17
 */
@Data
@AllArgsConstructor
public class SaveStockDto {

  Long skuId;
  Integer stock;
}
