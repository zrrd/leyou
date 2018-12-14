package com.leyou.service.controller.req;

import com.leyou.common.base.request.Req;
import java.util.List;
import lombok.Data;

/**
 * 保存商品请求.
 *
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Data
public class SaveGoodsReq implements Req {

  Long brandId;
  Long cid1;
  Long cid2;
  Long cid3;
  List<Sku> skus;
  SubTitle spuDetail;
  String subTitle;
  String title;

  @Data
  class Sku {

    Long id;
    Boolean enable;
    String images;
    String indexes;
    String ownSpec;
    Long price;
    Integer stock;
    String title;
  }

  @Data
  class SubTitle {

    String afterService;
    String description;
    String packingList;
    String specTemplate;
    String specifications;
  }

}
