package com.leyou.cart.pojo.domain;

import lombok.Data;

/**
 * @author shaoyijiong
 * @date 2019/1/24
 */
@Data
public class Cart {

  /**
   * 用户id
   */
  private Long userId;
  /**
   * 商品id
   */
  private Long skuId;
  /**
   * 标题
   */
  private String title;
  /**
   * 图片
   */
  private String image;
  /**
   * 加入购物车时的价格
   */
  private Long price;
  /**
   * 购买数量
   */
  private Integer num;
  /**
   * 商品规格参数
   */
  private String ownSpec;
}
