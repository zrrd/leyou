package com.leyou.service.mvc.req;

import com.leyou.common.base.request.Req;
import java.util.List;
import lombok.Data;

/**
 * @author shaoyijong
 * @date 2018/11/30
 */
@Data
public class SaveBrandReq implements Req {

  private String name;
  private String image;
  private Character letter;
  /**
   * 类目ids.
   */
  private List<Long> cids;
}
