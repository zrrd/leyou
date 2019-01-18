package com.leyou.auth.service;

import com.leyou.auth.client.UserClient;
import com.leyou.auth.jwt.JwtProperties;
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

  public String authentication(String username, String password) {
    return null;
  }
}
