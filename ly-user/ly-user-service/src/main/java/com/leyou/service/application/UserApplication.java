package com.leyou.service.application;

import com.google.common.collect.Maps;
import com.leyou.common.utils.NumberUtils;
import com.leyou.service.config.MqProperties;
import java.util.Map;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shaoyijiong
 * @date 2019/1/16
 */
@Service
public class UserApplication {

  private final RabbitTemplate rabbitTemplate;

  private final MqProperties mqProperties;

  @Autowired
  public UserApplication(RabbitTemplate rabbitTemplate, MqProperties mqProperties) {
    this.rabbitTemplate = rabbitTemplate;
    this.mqProperties = mqProperties;
  }

  /**
   * 发送短信
   */
  public void sendCode(String phone) {
    //生成6位随机数
    String code = NumberUtils.generateCode(6);
    Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
    map.put("code", code);
    map.put("phone", phone);
    rabbitTemplate
        .convertAndSend(mqProperties.getSmsExchange(), mqProperties.getSmsVerityRoutingKey(), map);
  }
}
