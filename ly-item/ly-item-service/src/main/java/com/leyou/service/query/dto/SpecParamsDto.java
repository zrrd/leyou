package com.leyou.service.query.dto;

/**
 * 规格参数.
 *
 * @author shaoyijiong
 * @date 2018/12/11
 */
public class SpecParamsDto {

  /**
   * 参数名.
   */
  String k;
  /**
   * 是否可搜索.
   */
  String searchable;
  /**
   * 是否为sku通用属性.
   */
  String global;
  /**
   * 有那些选项.
   */
  String options;
  /**
   * 是否为数字.
   */
  String numerical;
  /**
   * 单位.
   */
  String unit;
  /**
   * 数值类型参数数值分段.
   */
  String segments;
}
