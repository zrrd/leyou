package com.leyou.sms.service.impl;

import com.leyou.common.base.exception.ExceptionEnum;
import com.leyou.common.base.exception.LyException;
import com.leyou.sms.service.SmsService;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 云片渠道短信发送
 *
 * @author shaoyijiong
 * @date 2019/1/15
 */
@Slf4j
@Service
public class YunPianSmsServiceImpl implements SmsService {


  private static final String VERITY_PREFIX = "sms:verity:";
  private final YunPianProperties properties;
  private final RedisTemplate<String, String> redisTemplate;
  private YunpianClient client;

  @SuppressWarnings("CheckStyle")
  @Autowired
  public YunPianSmsServiceImpl(RedisTemplate<String, String> redisTemplate,
      YunPianProperties properties) {
    this.redisTemplate = redisTemplate;
    this.properties = properties;
    this.client = new YunpianClient(properties.getApikey()).init();
  }

  @Override
  public void sendVerificationCode(String code, String phone) {
    //按照手机号限流
    Long expire = redisTemplate.getExpire(VERITY_PREFIX + phone, TimeUnit.SECONDS);
    if (expire != null && expire > 0 && properties.getValidityPeriod() - expire < properties
        .getSendLimit()) {
      return;
    }
    Result<SmsSingleSend> result = singleSend(code, phone);
    if (result != null && result.getCode() == 0) {
      log.info("发送成功");
      //设置有效期为5分钟
      redisTemplate.opsForValue().set(VERITY_PREFIX + phone, code,
          properties.getValidityPeriod(), TimeUnit.SECONDS);
    } else {
      log.error("发送异常手机号{}", phone);
    }
  }


  private Result<SmsSingleSend> singleSend(String code, String phone) {
    Result<SmsSingleSend> result;
    Map<String, String> param = client.newParam(2);
    param.put("text", "【云片网】您的验证码是" + code);
    param.put("mobile", phone);

    try {
      result = client.sms().single_send(param);
    } catch (Exception e) {
      log.error("发送失败", e);
      throw new LyException(ExceptionEnum.SEND_SMS_ERROR);
    }
    return result;
  }
}
