package com.leyou.auth.jwt;

import com.leyou.auth.utils.RsaUtils;
import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shaoyijiong
 * @date 2019/1/18
 */
@Component
@ConfigurationProperties(prefix = "ly.jwt")
@Data
@Slf4j
public class JwtProperties {

  private String pubKeyUrl;
  private String priKeyUrl;
  /**
   * 过期时间 单位分钟
   */
  private int expire;
  private String cookieName;
  private int cookieMaxAge;

  /**
   * 公钥
   */
  private PublicKey publicKey;
  /**
   * 私钥
   */
  private PrivateKey privateKey;

  /**
   * 初始化公钥与私钥
   */
  @PostConstruct
  public void init() {
    try {
      publicKey = RsaUtils.getPublicKey(IOUtils.toByteArray(new URL(pubKeyUrl)));
      privateKey = RsaUtils.getPrivateKey(IOUtils.toByteArray(new URL(priKeyUrl)));
    } catch (Exception e) {
      log.error("初始化公钥与私钥失败", e);
    }

  }
}
