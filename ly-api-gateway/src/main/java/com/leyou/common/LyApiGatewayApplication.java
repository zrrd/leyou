package com.leyou.common;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * .
 * @author shaoyijoing
 */
@SpringCloudApplication
@EnableZuulProxy
public class LyApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(LyApiGatewayApplication.class, args);
  }
}
