package com.leyou.common.base.exception;

import lombok.Getter;

/**
 * @author shaoyijiong
 */
@Getter
public class LyException extends RuntimeException {

  private final Integer code;

  public LyException(ExceptionEnum exceptionEnum) {
    super(exceptionEnum.getMsg());
    this.code = exceptionEnum.getCode();
  }


}
