package com.leyou.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author shaoyijiong
 */
@EnableConfigurationProperties
@SpringBootApplication
public class LyAuthServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LyAuthServiceApplication.class, args);
  }

}

