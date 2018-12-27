package com.leyou.common.service.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 品牌内部接口
 *
 * @author shaoyijiong
 * @date 2018/12/27
 */
@SuppressWarnings("unused")
@RequestMapping("brand")
public interface BrandApi {

  /**
   * 根据ip获取品牌名称
   *
   * @param id 品牌id
   * @return 品牌名
   */
  @RequestMapping(value = "name",method = RequestMethod.GET)
  String queryBrandName(@RequestParam("id") Long id);
}
