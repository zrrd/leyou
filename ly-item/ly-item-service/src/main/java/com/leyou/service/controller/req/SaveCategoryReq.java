package com.leyou.service.controller.req;

import com.leyou.common.base.request.Req;
import lombok.Data;

/**
 * 保存分类请求
 *
 * @author shaoyijiong
 * @date 2018/12/25
 */
@Data
public class SaveCategoryReq implements Req {

  private String name;
  private Boolean isParent;
  private Long parentId;
  private Integer sort;
}
