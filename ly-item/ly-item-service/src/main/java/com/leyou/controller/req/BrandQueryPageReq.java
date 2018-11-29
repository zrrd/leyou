package com.leyou.controller.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * .
 * @author shaoyijiong
 * @date 2018/11/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BrandQueryPageReq extends Page {

  String key;
}
