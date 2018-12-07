package com.leyou.service.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * mybatis-plus 配置插件.
 *
 * @author 邵益炯
 * @date 2018/11/30
 */
@Configuration
@MapperScan(basePackages = {"com.leyou.query.mapper","com.leyou.application.mapper"})
public class MybatisPlusConfig {

  /**
   * SQL执行效率插件 设置 dev test 环境开启.
   */
  @Bean
  @Profile({"dev", "test"})
  public PerformanceInterceptor performanceInterceptor() {
    return new PerformanceInterceptor();
  }

  /**
   * 分页插件.
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }
}
