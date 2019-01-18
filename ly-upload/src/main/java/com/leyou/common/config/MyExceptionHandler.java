package com.leyou.common.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常拦截器
 *
 * @author shaoyijiong
 * @date 2018/12/4
 */
@ControllerAdvice
public class MyExceptionHandler {

  /**
   * 异常处理器.
   *
   * @param e 自定义的异常全部通过这个处理器处理
   * @return 自定义的异常信息, json格式 返回消息类型是正常状态
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  @ExceptionHandler(RuntimeException.class)
  public String handleException(RuntimeException e) {
    return e.getMessage();
  }
}
