package com.leyou.common.service.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import java.io.Serializable;

/**
 * 是否有效
 *
 * @author shaoyijiong
 * @date 2018/12/12
 */
public enum Valid implements IEnum {
  /**
   * 启用
   */
  ENABLE(true),
  /**
   * 删除
   */
  DELETE(false);

  private Boolean value;

  Valid(Boolean value) {
    this.value = value;
  }


  @Override
  public Serializable getValue() {
    return value;
  }
}
