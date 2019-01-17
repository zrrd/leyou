package com.leyou.service.application;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.leyou.service.application.mapper.StockMapper;
import com.leyou.service.pojo.domain.Stock;
import com.leyou.service.pojo.dto.application.SaveStockDto;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Service
public class StockApplication extends ServiceImpl<StockMapper, Stock> {

  /**
   * 保存stock
   */
  public void saveStock(List<SaveStockDto> stockDtoList) {
    List<Stock> stocks = Lists.newArrayList();
    stockDtoList.forEach(dto -> {
      Stock stock = Stock.newInstForSave(dto.getSkuId(), dto.getStock());
      stocks.add(stock);
    });
    this.saveBatch(stocks);
  }

  /**
   * 批量删除原来的sku 库存
   */
  public void deleteStock(List<Long> skuIds) {
    UpdateWrapper<Stock> qw = new UpdateWrapper<>();
    qw.in("sku_id", skuIds);
    Stock stock = new Stock();
    stock.delete(qw);
  }
}
