package com.leyou.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author shaoyijiong
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class LySearchApplication {

  public static void main(String[] args) {
    SpringApplication.run(LySearchApplication.class, args);
  }

}

