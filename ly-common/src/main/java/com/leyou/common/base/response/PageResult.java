package com.leyou.common.base.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * 分页结果.
 *
 * @author shoayijiong
 * @date 2018/11/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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


  /**
   * 分页助手.
   */
  public static <T> PageResult<T> getPageResult(IPage<T> page) {
    return new PageResult<>(page.getTotal(), page.getPages(), page.getRecords());
  }
}
