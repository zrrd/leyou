package com.leyou.service.application;

import com.leyou.service.pojo.domain.Stock;
import org.springframework.stereotype.Service;

/**
 * .
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Service
public class StockApplication {

  public void saveStock(Long skuId,Integer stockNum) {
    Stock stock = Stock.newInstForSave(skuId, stockNum);
    stock.insert();
  }
}
