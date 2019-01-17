package com.leyou.service.application.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 该类实现ApplicationContextAware接口,得到ApplicationContext对象,使用该对象的publishEvent方法发布事件
 * </p>
 *
 * @author shaoyijiong
 * @date 2018/10/26
 */
@Component
public class Publisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  public Publisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }


  public void publishEvent(ApplicationEvent event) {
    applicationEventPublisher.publishEvent(event);
  }
}
