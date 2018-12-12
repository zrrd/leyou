package com.leyou.service.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.leyou.service.enums.Saleable;
import com.leyou.service.enums.Valid;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 抽象商品表.
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@TableName("tb_spu")
public class Spu {

  Long id;
  String title;
  String subTitle;
  Long cid1;
  Long cid2;
  Long cid3;
  Long brandId;
  /**
   * 是否在售.
   */
  Saleable saleable;
  /**
   * 是否有效.
   */
  Valid valid;
  Date createTime;
  Date lastUpdateTime;

}
