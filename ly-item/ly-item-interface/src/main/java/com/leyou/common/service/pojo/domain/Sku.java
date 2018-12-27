package com.leyou.common.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.leyou.common.service.enums.SkuEnable;
import com.leyou.common.utils.EnumUtils;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品实体表
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@TableName("tb_sku")
public class Sku extends Model<Sku> {

  Long id;
  Long spuId;
  /**
   * 商品标题
   */
  String title;
  /**
   * 商品图片
   */
  String images;
  /**
   * 销售价格,单位为分
   */
  Long price;
  /**
   * 特有规格属性在spu属性模板中的对应下标组合
   */
  String indexes;
  /**
   * sku的特有规格参数键值对，json格式，反序列化时请使用linkedHashMap，保证有序
   */
  String ownSpec;
  /**
   * 是否有效
   */
  SkuEnable enable;
  Date createTime;
  Date lastUpdateTime;

  /**
   * 静态方法保存
   */
  public static Sku newInstForSave(Long spuId, Boolean enable, Long price, String images,
      String indexes, String ownSpec, String title) {
    return new Sku(null, spuId, title, images, price, indexes, ownSpec,
        EnumUtils.get(SkuEnable.class, enable), new Date(), new Date());
  }
}
