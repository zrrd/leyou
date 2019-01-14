package com.leyou.service.pojo.event;

import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * 商品事件
 *
 * @author shaoyijiong
 * @date 2019/1/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ItemEvent extends ApplicationEvent {

  private Long spuId;
  /**
   * add delete update
   */
  private String operating;

  /**
   * Create a new ApplicationEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public ItemEvent(Map source) {
    super(source);
    spuId = (Long) source.get("spuId");
    operating = (String) source.get("operating");
  }
}
