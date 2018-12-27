package com.leyou.common.service.application;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.leyou.common.service.application.mapper.StockMapper;
import com.leyou.common.service.pojo.domain.Stock;
import com.leyou.common.service.pojo.dto.application.SaveStockDto;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Service
public class StockApplication extends ServiceImpl<StockMapper, Stock> {

  /**
   * 保存stock.
   */
  public void saveStock(List<SaveStockDto> stockDtoList) {
    List<Stock> stocks = Lists.newArrayList();
    stockDtoList.forEach(dto -> {
      Stock stock = Stock.newInstForSave(dto.getSkuId(), dto.getStock());
      stocks.add(stock);
    });
    this.saveBatch(stocks);
  }
}
