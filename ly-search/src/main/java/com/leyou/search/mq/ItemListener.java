package com.leyou.search.mq;

import com.google.common.collect.ImmutableList;
import com.leyou.search.service.BuildService;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shaoyijiong
 * @date 2019/1/10
 */
@Component
public class ItemListener {

  private final BuildService buildService;

  @Autowired
  public ItemListener(BuildService buildService) {
    this.buildService = buildService;
  }

  /**
   * 消息队列的监听
   */
  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "ly.create.index.queue", durable = "true"),
      exchange = @Exchange(
          value = "ly.item.exchange",
          ignoreDeclarationExceptions = "true",
          type = ExchangeTypes.TOPIC),
      key = {"item.insert", "item.update"}))
  public void listenCreateAndUpdate(Long spuId) {
    if (spuId == null) {
      return;
    }
    //对索引库进行新增或修改
    buildService.insertIndex(ImmutableList.of(buildService.build(spuId)));
  }


  /**
   * 消息队列的监听
   */
  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "ly.create.index.queue", durable = "true"),
      exchange = @Exchange(
          value = "ly.item.exchange",
          ignoreDeclarationExceptions = "true",
          type = ExchangeTypes.TOPIC),
      key = {"item.delete"}))
  public void listenDelete(Long spuId) {
    if (spuId == null) {
      return;
    }
    //对索引库进行新增或修改
    buildService.insertIndex(ImmutableList.of(buildService.build(spuId)));
  }
}
