package com.leyou.service.application;

import com.google.common.collect.Maps;
import com.leyou.common.base.exception.ExceptionEnum;
import com.leyou.common.base.exception.LyException;
import com.leyou.common.utils.NumberUtils;
import com.leyou.service.config.MqProperties;
import com.leyou.service.mvc.req.RegisterReq;
import com.leyou.service.pojo.domain.User;
import com.leyou.service.utils.CodecUtils;
import java.util.Map;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author shaoyijiong
 * @date 2019/1/16
 */
@Service
public class UserApplication {

  private static final String VERITY_PREFIX = "sms:verity:";
  private final RabbitTemplate rabbitTemplate;
  private final MqProperties mqProperties;
  private final RedisTemplate<String, Object> redisTemplate;

  @SuppressWarnings("CheckStyle")
  @Autowired
  public UserApplication(RabbitTemplate rabbitTemplate, MqProperties mqProperties,
      RedisTemplate<String, Object> redisTemplate) {
    this.rabbitTemplate = rabbitTemplate;
    this.mqProperties = mqProperties;
    this.redisTemplate = redisTemplate;
  }

  /**
   * 发送短信
   */
  public void sendCode(String phone) {
    //生成6位随机数
    String code = NumberUtils.generateCode(6);
    Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
    map.put("code", code);
    map.put("phone", phone);
    rabbitTemplate
        .convertAndSend(mqProperties.getSmsExchange(), mqProperties.getSmsVerityRoutingKey(), map);
  }

  /**
   * 验证码
   */
  public void register(RegisterReq req) {
    // 校验验证码
    Integer code = (Integer) redisTemplate.opsForValue().get(VERITY_PREFIX + req.getPhone());
    if (code == null || !code.toString().equals(req.getCode())) {
      throw new LyException(ExceptionEnum.INVALID_VERIFY_CODE);
    }

    // 对密码进行加密
    String salt = CodecUtils.generateSalt();
    String password = CodecUtils.md5Hex(req.getPassword(), salt);

    // 写入数据库
    User u = User.newInstForSave(req.getUsername(), password, req.getPhone(), salt);
    u.insert();
  }
}
