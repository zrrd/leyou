package com.leyou.common.service.pojo.dto.application;

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
