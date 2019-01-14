package com.leyou.service.api;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 类目内部接口
 *
 * @author shaoyijiong
 * @date 2018/12/27
 */
@SuppressWarnings("unused")
@RequestMapping("category")
public interface CategoryApi {

  /**
   * 根据类目id查找类目名
   *
   * @param ids 类目id类别
   * @return 类目名
   */
  @RequestMapping(value = "names",method = RequestMethod.GET)
  List<String> queryNameByIds(@RequestParam("ids") List<Long> ids);
}
