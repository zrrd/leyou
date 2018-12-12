package com.leyou.service.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import java.io.Serializable;

/**
 * 是否销售.
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
public enum Saleable implements IEnum {
  /**
   * 在售.
   */
  SALE(true),
  /**
   * 不在售.
   */
  UN_SALE(false);

  private Boolean value;

  Saleable(Boolean value) {
    this.value = value;
  }

  public Saleable get(Boolean value) {
    for (Saleable e : Saleable.values()) {
      if (e.value.equals(value)) {
        return e;
      }
    }
    return null;
  }

  @Override
  public Serializable getValue() {
    return value;
  }
}
