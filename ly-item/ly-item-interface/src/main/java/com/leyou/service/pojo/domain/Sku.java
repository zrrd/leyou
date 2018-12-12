package com.leyou.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.leyou.service.enums.SkuEnable;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品实体表.
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@TableName("tb_sku")
public class Sku {

  Long id;
  Long spuId;
  /**
   * 商品标题.
   */
  String title;
  /**
   * 商品图片.
   */
  String images;
  /**
   * 销售价格,单位为分.
   */
  Long price;
  /**
   * 特有规格属性在spu属性模板中的对应下标组合.
   */
  String indexes;
  /**
   * sku的特有规格参数键值对，json格式，反序列化时请使用linkedHashMap，保证有序.
   */
  String ownSpec;
  /**
   * 是否有效.
   */
  SkuEnable enable;
  Date createTime;
  Date lastUpdateTime;
}
