package com.leyou.sms.service.impl;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 云片发送的配置信息
 *
 * @author shaoyijiong
 * @date 2019/1/15
 */
@Data
@Component
@ConfigurationProperties(prefix = "yunpian")
public class YunPianProperties {

  private String apikey;
  private String singleSendUrl;
  /**
   * 限流 单位秒
   */
  private Integer sendLimit = 60;
  /**
   * 有效期 单位秒
   */
  private Integer validityPeriod = 300;
}
