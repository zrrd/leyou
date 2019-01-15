package com.leyou.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class LySmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(LySmsApplication.class, args);
  }

}

