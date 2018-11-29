package com.leyou;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * .
 *
 * @author shaoyijiong
 */
@MapperScan(basePackages = "com.leyou.query.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class LyItemServiceApplication {

  /**
   * 分页插件.
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }

  public static void main(String[] args) {
    SpringApplication.run(LyItemServiceApplication.class, args);
  }
}
