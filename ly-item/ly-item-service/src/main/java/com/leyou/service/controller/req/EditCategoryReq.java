package com.leyou.service.controller.req;

import com.leyou.common.base.request.Req;
import lombok.Data;

/**
 * 修改分类请求
 *
 * @author shaoyijiong
 * @date 2018/12/25
 */
@Data
public class EditCategoryReq implements Req {

  String name;
}
