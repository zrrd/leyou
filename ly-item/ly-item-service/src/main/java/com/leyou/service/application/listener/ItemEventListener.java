package com.leyou.service.application.listener;

import com.leyou.service.mq.MqService;
import com.leyou.service.pojo.event.ItemEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author shaoyijiong
 * @date 2019/1/10
 */
@Component
public class ItemEventListener {

  private final MqService mqService;

  @Autowired
  public ItemEventListener(MqService mqService) {
    this.mqService = mqService;
  }

  /**
   * 监听事件
   */
  @EventListener(classes = ItemEvent.class)
  public void listenerItem(ItemEvent event) {
    String routingKey = "item." + event.getOperating();
    Long spuId = event.getSpuId();
    mqService
        .sendMsg(routingKey, spuId, spuId.toString());
  }
}
