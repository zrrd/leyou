package com.leyou.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * .
 *
 * @author shaoyijiong
 */
@EnableDiscoveryClient
@SpringBootApplication
public class LyItemServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LyItemServiceApplication.class, args);
  }
}
