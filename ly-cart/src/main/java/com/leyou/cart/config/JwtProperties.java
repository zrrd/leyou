package com.leyou.cart.config;

import com.leyou.auth.utils.RsaUtils;
import java.net.URL;
import java.security.PublicKey;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shaoyijiong
 * @date 2019/1/24
 */
@Component
@ConfigurationProperties(prefix = "ly.jwt")
@Data
@Slf4j
public class JwtProperties {

  private String pubKeyUrl;
  private String cookieName;

  /**
   * 公钥
   */
  private PublicKey publicKey;

  /**
   * 初始化公钥与私钥
   */
  @PostConstruct
  public void init() {
    try {
      publicKey = RsaUtils.getPublicKey(IOUtils.toByteArray(new URL(pubKeyUrl)));
    } catch (Exception e) {
      log.error("初始化公钥与私钥失败", e);
    }

  }
}