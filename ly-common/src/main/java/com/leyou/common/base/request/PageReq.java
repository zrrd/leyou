package com.leyou.common.base.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
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
   * 当前页 默认为第一页
   */
  Integer page = 1;
  /**
   * 每页大小 默认为10条
   */
  Integer rows = 10;
  /**
   * 排序字段.
   */
  String sortBy;
  /**
   * 是否为降序.
   */
  Boolean desc;

  /**
   * 转换page类型.
   */
  public Page getPages() {
    Page page = new Page(this.page, rows);
    if (!Strings.isNullOrEmpty(sortBy)) {
      if (desc) {
        page.setDesc(sortBy);
      } else {
        page.setAsc(sortBy);
      }
    }
    return page;
  }
}
