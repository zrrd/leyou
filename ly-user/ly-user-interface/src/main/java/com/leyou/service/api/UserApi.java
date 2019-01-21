package com.leyou.service.api;

import com.leyou.service.pojo.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shaoyijiong
 * @date 2019/1/18
 */
public interface UserApi {

  /**
   * 查询用户接口
   *
   * @param username 用户名
   * @param password 密码
   * @return 用户
   */
  @GetMapping("query")
  User queryUser(
      @RequestParam("username") String username,
      @RequestParam("password") String password);
}
