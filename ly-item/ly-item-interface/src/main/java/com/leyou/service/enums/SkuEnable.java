package com.leyou.service.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import java.io.Serializable;

/**
 * sku是否有效.
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
public enum SkuEnable implements IEnum {
  /**
   * 启用.
   */
  ENABLE(true),
  /**
   * 不启用.
   */
  NOT_ENABLE(false);

  private Boolean value;

  SkuEnable(Boolean value) {
    this.value = value;
  }

  /**
   * 通过值获取枚举.
   */
  public static SkuEnable get(Boolean value) {
    for (SkuEnable e : SkuEnable.values()) {
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
