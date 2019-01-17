package com.leyou.service.config;

import com.google.common.collect.Maps;
import com.leyou.common.base.exception.LyException;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常拦截器.
 *
 * @author shaoyijiong
 * @date 2018/12/4
 */
@SuppressWarnings("Duplicates")
@Slf4j
@ControllerAdvice
public class MyExceptionHandler {


  /**
   * 自定义异常处理
   */
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  @ExceptionHandler(LyException.class)
  public Map handleException(LyException e) {
    log.error("业务错误", e);
    return errorJson(e.getCode(), e.getMessage());
  }

  /**
   * 参数错误
   */
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  @ExceptionHandler(value = {ValidationException.class, BindException.class})
  public Map handleException(Exception e) {
    String message;
    log.error("参数错误", e);
    if (e instanceof BindException) {
      BindException bindException = (BindException) e;
      BindingResult bindingResult = bindException.getBindingResult();
      message = bindingResult.getAllErrors().get(0).getDefaultMessage();
    } else if (e instanceof ConstraintViolationException) {
      ConstraintViolationException violationException = (ConstraintViolationException) e;
      message = violationException.getConstraintViolations().iterator().next().getMessageTemplate();
    } else {
      message = e.getMessage();
    }
    return errorJson(400, message);
  }

  /**
   * 服务器异常
   *
   * @param e 自定义的异常全部通过这个处理器处理
   * @return 自定义的异常信息, json格式 返回消息类型是正常状态
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  @ExceptionHandler(RuntimeException.class)
  public Map handleException(RuntimeException e) {
    log.error("服务器错误", e);
    return errorJson(500, e.getMessage());
  }

  private Map errorJson(Integer code, String message) {
    Map<String, Object> resp = Maps.newHashMapWithExpectedSize(3);
    resp.put("status", code);
    resp.put("message", message);
    resp.put("timestamp", System.currentTimeMillis());
    return resp;
  }


}
