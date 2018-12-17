package com.leyou.service.application;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.leyou.service.application.dto.SaveStockDto;
import com.leyou.service.application.mapper.StockMapper;
import com.leyou.service.pojo.domain.Stock;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Service
public class StockApplication extends ServiceImpl<StockMapper, Stock> {

  /**
   * 保存stock.
   */
  public void saveStock(List<SaveStockDto> stockDtos) {
    List<Stock> stocks = Lists.newArrayList();
    stockDtos.forEach(dto -> {
      Stock stock = Stock.newInstForSave(dto.getSkuId(), dto.getStock());
      stocks.add(stock);
    });
    this.saveBatch(stocks);
  }
}
