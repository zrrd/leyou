package com.leyou.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author shaoyijiong
 */
@EnableConfigurationProperties
@SpringBootApplication
public class LyUserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LyUserServiceApplication.class, args);
  }

}

