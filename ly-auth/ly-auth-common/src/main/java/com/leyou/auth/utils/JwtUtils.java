package com.leyou.auth.utils;

import com.leyou.auth.entiy.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;


/**
 * @author shaoyijiong
 */
public class JwtUtils {

  /**
   * 私钥加密token
   *
   * @param userInfo 载荷中的数据
   * @param privateKey 私钥
   * @param expireMinutes 过期时间，单位秒
   */
  public static String generateToken(UserInfo userInfo, PrivateKey privateKey, int expireMinutes) {
    return Jwts.builder()
        .claim(JwtContains.JWT_KEY_ID, userInfo.getId())
        .claim(JwtContains.JWT_KEY_USER_NAME, userInfo.getUsername())
        .setExpiration(DateUtils.addMinutes(new Date(), expireMinutes))
        .signWith(SignatureAlgorithm.RS256, privateKey)
        .compact();
  }

  /**
   * 私钥加密token
   *
   * @param userInfo 载荷中的数据
   * @param privateKey 私钥字节数组
   * @param expireMinutes 过期时间，单位秒
   */
  public static String generateToken(UserInfo userInfo, byte[] privateKey, int expireMinutes)
      throws Exception {
    return Jwts.builder()
        .claim(JwtContains.JWT_KEY_ID, userInfo.getId())
        .claim(JwtContains.JWT_KEY_USER_NAME, userInfo.getUsername())
        .setExpiration(DateUtils.addMinutes(new Date(), expireMinutes))
        .signWith(SignatureAlgorithm.RS256, RsaUtils.getPrivateKey(privateKey))
        .compact();
  }

  /**
   * 公钥解析token
   *
   * @param token 用户请求中的token
   * @param publicKey 公钥
   */
  private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
    return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
  }

  /**
   * 公钥解析token
   *
   * @param token 用户请求中的token
   * @param publicKey 公钥字节数组
   */
  private static Jws<Claims> parserToken(String token, byte[] publicKey) throws Exception {
    return Jwts.parser().setSigningKey(RsaUtils.getPublicKey(publicKey))
        .parseClaimsJws(token);
  }

  /**
   * 获取token中的用户信息
   *
   * @param token 用户请求中的令牌
   * @param publicKey 公钥
   * @return 用户信息
   */
  public static UserInfo getInfoFromToken(String token, PublicKey publicKey) {
    Jws<Claims> claimsJws = parserToken(token, publicKey);
    Claims body = claimsJws.getBody();
    return new UserInfo(
        ObjectUtils.toLong(body.get(JwtContains.JWT_KEY_ID)),
        ObjectUtils.toString(body.get(JwtContains.JWT_KEY_USER_NAME))
    );
  }

  /**
   * 获取token中的用户信息
   *
   * @param token 用户请求中的令牌
   * @param publicKey 公钥
   * @return 用户信息
   */
  public static UserInfo getInfoFromToken(String token, byte[] publicKey) throws Exception {
    Jws<Claims> claimsJws = parserToken(token, publicKey);
    Claims body = claimsJws.getBody();
    return new UserInfo(
        ObjectUtils.toLong(body.get(JwtContains.JWT_KEY_ID)),
        ObjectUtils.toString(body.get(JwtContains.JWT_KEY_USER_NAME))
    );
  }
}