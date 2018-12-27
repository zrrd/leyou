package com.leyou.common.service.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 规格参数内部接口
 *
 * @author shaoyijiong
 * @date 2018/12/27
 */
@SuppressWarnings("unused")
@RequestMapping("spec")
public interface SpecApi {

  /**
   * 规格请求
   *
   * @param id 品类id
   * @return 规格详情
   */
  @RequestMapping(value = "{id}",method = RequestMethod.GET)
  String querySpecifications(@PathVariable("id") Long id);
}
