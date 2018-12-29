package com.leyou.search.controller.req;

import com.leyou.common.base.request.PageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shaoyijiong
 * @date 2018/12/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchPageReq extends PageReq {

  public static final int DEFAULT_ROWS = 20;
  private String key;
}
