package com.leyou.sms.service.impl;

import com.leyou.sms.service.SmsService;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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
  private static final String CODE = "code";
  private final YunPianProperties properties;
  private final RedisTemplate<String, String> redisTemplate;
  private RestTemplate restTemplate = new RestTemplate();

  @Autowired
  public YunPianSmsServiceImpl(RedisTemplate<String, String> redisTemplate,
      YunPianProperties properties) {
    this.redisTemplate = redisTemplate;
    this.properties = properties;
  }

  @Override
  public void sendVerificationCode(String code, String phone) {
    //按照手机号限流
    String lastTime = redisTemplate.opsForValue().get(VERITY_PREFIX + phone);
    if (lastTime != null && System.currentTimeMillis() - Long.valueOf(lastTime) < properties
        .getSendLimit()) {
      return;
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("apikey", properties.getApikey());
    params.add("text", "【云片网】您的验证码是" + code);
    params.add("mobile", phone);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    Map resp;
    try {
      resp = restTemplate.postForObject(properties.getSingleSendUrl(), request, Map.class);
    } catch (Exception e) {
      log.error("发送失败", e);
      return;
    }
    if (resp != null && (Integer) resp.get(CODE) == 0) {
      log.info("发送成功");
      //设置有效期为15分钟
      redisTemplate.opsForValue()
          .set(VERITY_PREFIX + phone, String.valueOf(System.currentTimeMillis()),
              properties.getValidityPeriod(), TimeUnit.MINUTES);
    } else {
      log.error("发送异常手机号{}", phone);
    }
  }
}
