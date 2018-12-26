package com.leyou.service.pojo.dto.query;

import lombok.Data;

/**
 * .
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@Data
public class SpuQueryDto {

  Long id;
  String title;
  String subTitle;
  Long brandId;
  Long cid1;
  Long cid2;
  Long cid3;
  String cname;
  String bname;
  Boolean saleable;
}
