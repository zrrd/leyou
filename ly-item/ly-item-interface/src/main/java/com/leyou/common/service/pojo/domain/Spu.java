package com.leyou.common.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.leyou.common.service.enums.Saleable;
import com.leyou.common.service.enums.Valid;
import com.leyou.common.utils.EnumUtils;
import java.sql.Timestamp;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 抽象商品表
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@TableName("tb_spu")
public class Spu extends Model<Spu> {

  Long id;
  String title;
  String subTitle;
  Long cid1;
  Long cid2;
  Long cid3;
  Long brandId;
  /**
   * 是否在售
   */
  Saleable saleable;
  /**
   * 是否有效
   */
  Valid valid;
  Date createTime;
  Date lastUpdateTime;

  public Spu(Long id, String title, String subTitle, Long cid1, Long cid2, Long cid3,
      Long brandId, Boolean saleable, Boolean valid, Timestamp createTime, Timestamp lastUpdateTime) {
    this.id = id;
    this.title = title;
    this.subTitle = subTitle;
    this.cid1 = cid1;
    this.cid2 = cid2;
    this.cid3 = cid3;
    this.brandId = brandId;
    this.saleable = EnumUtils.get(Saleable.class,saleable);
    this.valid = EnumUtils.get(Valid.class,valid);
    this.createTime = createTime;
    this.lastUpdateTime = lastUpdateTime;
  }

  public static Spu newInstForSave(Long brandId, Long cid1, Long cid2, Long cid3, String title,
      String subTitle) {
    return new Spu(null, title, subTitle, cid1, cid2, cid3,
        brandId, Saleable.SALE, Valid.ENABLE, new Date(), new Date());
  }
}
