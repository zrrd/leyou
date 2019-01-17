package com.leyou.service.utils;

import com.google.common.base.Strings;
import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 加密
 *
 * @author shaoyijiong
 * @date 2019/1/17
 */
public class CodecUtils {

  /**
   * md5盐值加密
   */
  public static String md5Hex(String data, String salt) {
    if (Strings.isNullOrEmpty(salt)) {
      salt = data.hashCode() + "";
    }
    return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(data));
  }

  /**
   * sha 盐值加密
   */
  public static String shaHex(String data, String salt) {
    if (Strings.isNullOrEmpty(salt)) {
      salt = data.hashCode() + "";
    }
    return DigestUtils.sha512Hex(salt + DigestUtils.sha512Hex(data));
  }

  /**
   * 生成盐值
   */
  public static String generateSalt() {
    return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
  }
}
