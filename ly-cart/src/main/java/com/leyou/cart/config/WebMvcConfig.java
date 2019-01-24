package com.leyou.cart.config;

import com.leyou.cart.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author shaoyijiong
 * @date 2019/1/24
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final JwtProperties jwtProperties;

  @Autowired
  public WebMvcConfig(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
  }

  @Bean
  public HandlerInterceptor loginInterceptor() {
    return new LoginInterceptor(jwtProperties);
  }
}
