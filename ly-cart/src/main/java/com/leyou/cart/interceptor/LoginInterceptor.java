package com.leyou.cart.interceptor;

import com.google.common.base.Strings;
import com.leyou.auth.entiy.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author shaoyijiong
 * @date 2019/1/24
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

  private JwtProperties jwtProperties;

  // 定义一个线程域，存放登录用户
  private static final ThreadLocal<UserInfo> TL = new ThreadLocal<>();

  public LoginInterceptor(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
    if (Strings.isNullOrEmpty(token)) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return false;
    }
    try {
      //解析成功
      UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
      TL.set(userInfo);
      return true;
    } catch (Exception e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return false;
    }
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {
    TL.remove();
  }

  public static UserInfo getLoginUser() {
    return TL.get();
  }

}
