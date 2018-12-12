package com.leyou.service.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import java.io.Serializable;

/**
 * 是否有效.
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
public enum Valid implements IEnum {
  /**
   * 启用.
   */
  ENABLE(true),
  /**
   * 删除.
   */
  DELETE(false);

  private Boolean value;

  Valid(Boolean value) {
    this.value = value;
  }

  public Valid get(Boolean value) {
    for (Valid e : Valid.values()) {
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
