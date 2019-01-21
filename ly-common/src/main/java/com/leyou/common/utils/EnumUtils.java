package com.leyou.common.utils;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 枚举工具类
 *
 * @author shaoyijiong
 * @date 2018/12/27
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumUtils {

  /**
   * 通过值获取枚举
   */
  public static <T extends IEnum> T get(Class<T> enumClass, Object o) {
    //获取该枚举类的所有枚举
    T[] enumConstants = enumClass.getEnumConstants();
    for (T t : enumConstants) {
      if (t.getValue().equals(o)) {
        return t;
      }
    }
    return null;
  }
}
