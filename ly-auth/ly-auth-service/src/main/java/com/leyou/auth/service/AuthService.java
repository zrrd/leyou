package com.leyou.auth.service;

import com.leyou.auth.client.UserClient;
import com.leyou.auth.entiy.UserInfo;
import com.leyou.auth.jwt.JwtProperties;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.base.exception.ExceptionEnum;
import com.leyou.common.base.exception.LyException;
import com.leyou.service.pojo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shaoyijiong
 * @date 2019/1/18
 */
@Service
public class AuthService {

  private final UserClient userClient;
  private final JwtProperties properties;

  @Autowired
  public AuthService(JwtProperties properties, UserClient userClient) {
    this.properties = properties;
    this.userClient = userClient;
  }

  /**
   * 校验用户 生成token
   */
  public String authentication(String username, String password) {
    User user = userClient.queryUser(username, password);
    if (user == null || user.getId() == null) {
      throw new LyException(ExceptionEnum.USER_NOT_EXIST);
    }
    return JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()),
        properties.getPrivateKey(), properties.getExpire());
  }
}
