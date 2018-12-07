package com.leyou.service.controller.req;

import com.leyou.common.base.request.PageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * .
 * @author shaoyijiong
 * @date 2018/11/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BrandQueryPageReq extends PageReq {

  String key;
}
