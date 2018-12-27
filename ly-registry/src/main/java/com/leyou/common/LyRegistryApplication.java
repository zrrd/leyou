package com.leyou.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心
 *
 * @author shaoyijong
 */
@EnableEurekaServer
@SpringBootApplication
public class LyRegistryApplication {

  public static void main(String[] args) {
    SpringApplication.run(LyRegistryApplication.class, args);
  }
}
