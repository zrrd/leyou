package com.leyou.common.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 异常枚举.
 *
 * @author shaoyijiong
 * @date 2018/12/17
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  ExceptionEnum {
  /**
   * 错误枚举.
   */
  UPLOAD_MISMATCH(500,"文件上传失败,文件类型不匹配"),
  UPLOAD_IMAGE_ERROR(500,"上传失败图片错误"),
  UPLOAD_ERROR(500,"文件上传失败"),
  BRAND_NAME_USED(500,"该品牌名称已被使用,请使用其他品牌名称"),
  ;

  /**
   * 错误编码.
   */
  private int code;
  /**
   * 错误消息.
   */
  private String msg;
}
