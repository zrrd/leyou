package com.leyou.auth.controller;

import com.google.common.base.Strings;
import com.leyou.auth.entiy.UserInfo;
import com.leyou.auth.jwt.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
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
    String token = authService.authentication(username, password);
    if (Strings.isNullOrEmpty(token)) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
    CookieUtils.newBuilder(response).request(request).maxAge(prop.getCookieMaxAge()).httpOnly()
        .build(prop.getCookieName(), token);

    return ResponseEntity.ok().build();
  }

  @GetMapping("verify")
  public ResponseEntity<UserInfo> verifyUser(@CookieValue("LY_TOKEN") String token) {
    try {
      // 从token中解析token信息
      UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
      // 解析成功返回用户信息
      return ResponseEntity.ok(userInfo);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // 出现异常则，响应500
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
