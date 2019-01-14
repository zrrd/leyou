package com.leyou.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * 库存信息
 *
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@TableName("tb_stock")
public class Stock extends Model<Stock> {


  @TableId
  Long skuId;

  /**
   * 可秒杀库存
   */
  Integer seckillStock;
  /**
   * 可秒杀总数
   */
  Integer seckillTotal;

  /**
   * 库存数量
   */
  Integer stock;

  /**
   * 静态方法 保存
   */
  public static Stock newInstForSave(@NonNull Long skuId, @NonNull Integer stock) {
    Stock stockBean = new Stock();
    stockBean.setSkuId(skuId);
    stockBean.setStock(stock);
    return stockBean;
  }
}
