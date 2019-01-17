package com.leyou.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author shaoyijiong
 */
@EnableConfigurationProperties
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class LySmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(LySmsApplication.class, args);
  }

}

