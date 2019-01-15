package com.leyou.sms.service;

/**
 * 提供短信发送服务
 *
 * @author shaoyijiong
 * @date 2019/1/15
 */
public interface SmsService {

  /**
   * 发送验证码
   *
   * @param code 验证码
   * @param phone 短信
   */
  void sendVerificationCode(String code, String phone);
}
