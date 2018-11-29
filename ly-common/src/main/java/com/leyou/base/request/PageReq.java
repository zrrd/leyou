package com.leyou.base.request;

import lombok.Data;

/**
 * 分页请求.
 *
 * @author shaoyijiong
 * @date 2018/11/29
 */
@Data
public class PageReq {

  /**
   * 当前页.
   */
  Integer page;
  /**
   * 每页大小.
   */
  Integer rows;
  /**
   * 排序字段.
   */
  String sortBy;
  /**
   * 是否为降序.
   */
  Boolean desc;
}
