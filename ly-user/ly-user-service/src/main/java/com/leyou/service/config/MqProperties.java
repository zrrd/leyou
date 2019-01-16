package com.leyou.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shaoyijiong
 * @date 2019/1/16
 */
@Data
@Component
@ConfigurationProperties(prefix = "ly.mq")
public class MqProperties {

  private String smsExchange;
  private String smsVerityRoutingKey;
}
