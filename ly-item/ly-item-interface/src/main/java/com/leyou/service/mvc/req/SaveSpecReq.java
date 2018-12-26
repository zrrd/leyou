package com.leyou.service.mvc.req;

import com.leyou.common.base.request.Req;
import lombok.Data;

/**
 * 保存或者修改规格参数.
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
@Data
public class SaveSpecReq implements Req {

  Long categoryId;
  String specifications;
}
