package com.leyou.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 库存信息.
 *
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "newInstForSave")
@TableName("tb_stock")
public class Stock extends Model<Stock> {

  @NonNull
  @TableId
  Long skuId;

  /**
   * 可秒杀库存.
   */
  Integer seckillStock;
  /**
   * 可秒杀总数.
   */
  Integer seckillTotal;

  /**
   * 库存数量.
   */
  @NonNull
  Integer stock;
}
