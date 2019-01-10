package com.leyou.goods.mq;

import com.leyou.goods.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class ItemListener {

  private final GoodsService goodsService;

  @Autowired
  public ItemListener(GoodsService goodsService) {
    this.goodsService = goodsService;
  }

  /**
   * 消息队列的监听
   */
  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "ly.create.detail.queue", durable = "true"),
      exchange = @Exchange(
          value = "ly.item.exchange",
          ignoreDeclarationExceptions = "true",
          type = ExchangeTypes.TOPIC),
      key = {"item.insert", "item.update"}))
  public void listenCreateAndUpdate(Long spuId) {
    log.info("收到消息创建{}", spuId);
    if (spuId == null) {
      return;
    }
    //对索引库进行新增或修改
    goodsService.createHtml(null,spuId);
  }


  /**
   * 消息队列的监听
   */
  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "ly.delete.detail.queue", durable = "true"),
      exchange = @Exchange(
          value = "ly.item.exchange",
          ignoreDeclarationExceptions = "true",
          type = ExchangeTypes.TOPIC),
      key = {"item.delete"}))
  public void listenDelete(Long spuId) {
    log.info("收到消息删除{}", spuId);
    if (spuId == null) {
      return;
    }
    //对索引库进行新增或修改
    goodsService.createHtml(null,spuId);
  }
}
