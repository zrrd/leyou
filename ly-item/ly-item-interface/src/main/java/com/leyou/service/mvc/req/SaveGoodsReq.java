package com.leyou.service.mvc.req;

import com.leyou.common.base.request.Req;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 保存商品请求.
 *
 * @author shaoyijiong
 * @date 2018/12/14
 */
@Data
@AllArgsConstructor
public class SaveGoodsReq implements Req {

  private Long id;
  private Long brandId;
  private String title;
  private String subTitle;
  private SpuDetailBean spuDetail;
  private Long cid1;
  private Long cid2;
  private Long cid3;
  private List<CategoriesBean> categories;
  private List<SkusBean> skus;

  @Data
  private static class SpuDetailBean {

    private String packingList;
    private String afterService;
    private String description;
    private String specTemplate;
    private String specifications;
  }

  @Data
  private static class CategoriesBean {

    private String name;
    private Long id;
  }

  @Data
  public static class SkusBean {

    private Long id;
    private Boolean enable;
    private String title;
    private String images;
    private Long price;
    private String ownSpec;
    private String indexes;
    private Integer stock;
  }
}
