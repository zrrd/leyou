package com.leyou.common.service.pojo.dto.query;

import java.util.Date;
import lombok.Data;

/**
 * @author shaoyijiong
 * @date 2018/12/28
 */
@Data
public class SpuDto {

  Long id;
  String title;
  String subTitle;
  Long cid1;
  Long cid2;
  Long cid3;
  Long brandId;
  Boolean saleable;
  Boolean valid;
  Date createTime;
  Date lastUpdateTime;
}
