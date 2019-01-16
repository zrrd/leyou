package com.leyou.service.controller;

import com.leyou.service.application.UserApplication;
import com.leyou.service.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shaoyijiong
 * @date 2019/1/16
 */
@RestController
public class UserController {

  private final UserApplication application;
  private final UserQuery query;

  @Autowired
  public UserController(UserApplication application, UserQuery query) {
    this.application = application;
    this.query = query;
  }

  /**
   * 校验用户数据
   *
   * @param data 要校验的用户数据
   * @param type 要校验的数据类型：1，用户名；2，手机 非必须
   * @return 是否可用
   */
  @GetMapping(value = {"/check/{data}/{type}","/check/{data}"})
  public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data,
      @PathVariable(value = "type", required = false) Integer type) {
    //默认查询用户名
    type = type == null ? 1 : type;
    Boolean check = query.checkData(data, type);
    if (check == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.ok(check);
  }

  /**
   * 发送短信验证码
   *
   * @param phone 用户电话
   */
  @PostMapping("code")
  public ResponseEntity<Void> sendCode(String phone) {
    application.sendCode(phone);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
