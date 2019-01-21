package com.leyou.common.config;

import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 登录校验
 *
 * @author shaoyijiong
 * @date 2019/1/21
 */
@Component
public class LoginFilter extends ZuulFilter {

  private final JwtProperties properties;
  private final FilterProperties filterProperties;

  @Autowired
  public LoginFilter(JwtProperties properties, FilterProperties filterProperties) {
    this.properties = properties;
    this.filterProperties = filterProperties;
  }

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 5;
  }


  @Override
  public boolean shouldFilter() {
    // 获取上下文
    RequestContext ctx = RequestContext.getCurrentContext();
    // 获取request
    HttpServletRequest req = ctx.getRequest();
    // 获取路径
    String requestUri = req.getRequestURI();
    // 判断白名单
    return !isAllowPath(requestUri);
  }

  private boolean isAllowPath(String requestUri) {
    // 定义一个标记
    boolean flag = false;
    // 遍历允许访问的路径
    for (String path : this.filterProperties.getAllowPaths()) {
      // 然后判断是否是符合
      if (requestUri.startsWith(path)) {
        flag = true;
        break;
      }
    }
    return flag;
  }

  @Override
  public Object run() {
    // 获取上下文
    RequestContext context = RequestContext.getCurrentContext();
    // 获取request
    HttpServletRequest request = context.getRequest();

    // 获取token
    String token = CookieUtils.getCookieValue(request, this.properties.getCookieName());
    // 校验
    try {
      // 校验通过什么都不做，即放行
      JwtUtils.getInfoFromToken(token, this.properties.getPublicKey());
    } catch (Exception e) {
      // 校验出现异常，返回403
      context.setSendZuulResponse(false);
      context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
    }
    return null;
  }
}
