package com.leyou.base.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 分页结果.
 *
 * @author shoayijiong
 * @date 2018/11/29
 */
@Data
@AllArgsConstructor
public class PageResult<T> {

  /**
   * 总条数.
   */
  private Long total;
  /**
   * 总页数.
   */
  private Long totalPage;
  /**
   * 当前页数据.
   */
  private List<T> items;

  private PageResult() {
  }


  /**
   * 分页助手.
   */
  public static PageResult getPageResult(IPage page) {
    PageResult pageResult = new PageResult<>();
    pageResult.items = page.getRecords();
    pageResult.total = page.getTotal();
    pageResult.totalPage = page.getPages();
    return pageResult;
  }
}
