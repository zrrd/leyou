package com.leyou.item;

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
public class LyItemInterfaceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LyItemInterfaceApplication.class, args);
  }
}
