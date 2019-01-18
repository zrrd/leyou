package com.leyou.auth.controller;

import com.leyou.auth.jwt.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.common.utils.CookieUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shaoyijiong
 * @date 2019/1/18
 */
@RestController
public class AuthController {

  private final JwtProperties prop;
  private final AuthService authService;

  @Autowired
  public AuthController(JwtProperties prop, AuthService authService) {
    this.prop = prop;
    this.authService = authService;
  }

  /**
   * 登录授权
   */
  @PostMapping("accredit")
  public ResponseEntity<Void> authentication(String username, String password,
      HttpServletRequest request,
      HttpServletResponse response) {
    // 登录校验
    String token = this.authService.authentication(username, password);
    if (StringUtils.isBlank(token)) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
    CookieUtils.setCookie(request, response, prop.getCookieName(),
        token, prop.getCookieMaxAge(), true);
    return ResponseEntity.ok().build();
  }
}
