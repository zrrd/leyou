package com.leyou.base.exception;

import lombok.Getter;

/**
 * .
 *
 * @author shaoyijiong
 */
@SuppressWarnings("unused")
@Getter
public abstract class BaseException extends RuntimeException {

  /**
   * 异常状态码，请在exception.md文件中注明状态码的含义
   */
  private final Integer code;

  public BaseException(int code) {
    this.code = code;
  }

  /**
   * 构造1.
   *
   * @param message 异常情况说明
   * @param code 异常状态码
   */
  public BaseException(String message, int code) {
    super(message);
    this.code = code;
  }

  /**
   * 构造2.
   *
   * @param message 异常情况说明
   * @param code 异常状态码
   * @param cause 源异常
   */
  public BaseException(String message, int code, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  /**
   * 构造3.
   *
   * @param cause 源异常
   * @param code 异常状态码
   */
  public BaseException(Throwable cause, int code) {
    super(cause);
    this.code = code;
  }

  /**
   * 构造4.
   *
   * @param message 异常情况说明
   * @param code 异常状态码
   * @param cause 源异常
   * @param enableSuppression 是否启用抑制
   * @param writableStackTrace 堆栈跟踪是否应该是可写的
   */
  public BaseException(String message, int code, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.code = code;
  }
}
