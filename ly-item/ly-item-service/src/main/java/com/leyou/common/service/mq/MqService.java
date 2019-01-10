package com.leyou.common.service.mq;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息队列相关操作
 *
 * @author shaoyijiong
 * @date 2019/1/9
 */
@Slf4j
@Service
public class MqService {

  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public MqService(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  /**
   * 自动 Java 对象包装成 Message 对象，Java 对象需要实现 Serializable 序列化接口
   */
  public void sendMsg(Object msg) {
    rabbitTemplate.convertAndSend(msg);
  }

  /**
   * 方便确认是那条消息收到了 自动 Java 对象包装成 Message 对象，Java 对象需要实现 Serializable 序列化接口
   */
  public void sendMsg(Object msg, String id) {
    rabbitTemplate.correlationConvertAndSend(msg, new CorrelationData(id));
  }

  public void sendMsg(String routingKey, Object msg, String id) {
    rabbitTemplate.convertAndSend(routingKey, msg, new CorrelationData(id));
  }

  /**
   * 消息的cas确认
   */
  @PostConstruct
  private void setup() {
    //消息发送完毕后 调用次方法ack代表发送是否成功
    rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
      String id = correlationData.getId();

      //ack 为true 表示MQ已经收到消息
      if (ack) {
        log.info("id为{}的消息发送成功", id);
        return;
      }
      log.error("id为{}的消息发送失败" + cause, id);
    });
  }
}
