package com.leyou.common.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常枚举.
 *
 * @author shaoyijiong
 * @date 2018/12/17
 */
@Getter
@AllArgsConstructor
public enum ExceptionEnum {
  /**
   * 错误枚举
   */
  UPLOAD_MISMATCH(400, "文件上传失败,文件类型不匹配"),
  UPLOAD_IMAGE_ERROR(400, "上传失败,图片错误"),
  UPLOAD_ERROR(500, "文件上传失败"),
  BRAND_NAME_USED(500, "该品牌名称已被使用,请使用其他品牌名称"),
  INVALID_USER_DATA_TYPE(400,"用户的数据类型无效"),
  SEND_SMS_ERROR(500, "发送短信异常"),
  INVALID_VERIFY_CODE(400,"无效的验证码"),
  USER_NOT_EXIST(400, "用户名或者密码错误"),
  ;

  /**
   * 错误编码
   */
  private int code;
  /**
   * 错误消息
   */
  private String msg;
}
