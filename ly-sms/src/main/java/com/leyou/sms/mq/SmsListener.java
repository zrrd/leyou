package com.leyou.sms.mq;

import com.leyou.sms.service.SmsService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信队列监听
 *
 * @author shaoyijiong
 * @date 2019/1/15
 */
@Slf4j
@Component
public class SmsListener {

  private final SmsService smsService;

  @Autowired
  public SmsListener(SmsService smsService) {
    this.smsService = smsService;
  }

  /**
   * 监听验证码发送任务
   *
   * @param msg 消息内容
   */
  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "ly.sms.verity.queue", durable = "true"),
      exchange = @Exchange(value = "ly.sms.exchange",
          ignoreDeclarationExceptions = "true",
          type = ExchangeTypes.TOPIC),
      key = "ly.sms.verity"
  ))
  public void smsVerityCodeListener(Map<String, String> msg) {
    log.info("收到消息" + msg);
    smsService.sendVerificationCode(msg.get("code"),msg.get("phone"));
  }
}
