package com.leyou.service.mvc.req;

import com.leyou.common.base.request.PageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shaoyijiong
 * @date 2018/11/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SpuQueryPageReq extends PageReq {

  String key;
  Boolean saleable;
}
