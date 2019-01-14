package com.leyou.service.mvc.req;

import com.leyou.common.base.request.Req;
import java.util.List;
import lombok.Data;

/**
 * 修改分类请求
 *
 * @author shaoyijiong
 * @date 2018/12/25
 */
@Data
public class EditCategoryReq implements Req {

  private Long id;
  private String name;
  private String image;
  private Character letter;
  /**
   * 类目ids.
   */
  private List<Long> cids;
}
