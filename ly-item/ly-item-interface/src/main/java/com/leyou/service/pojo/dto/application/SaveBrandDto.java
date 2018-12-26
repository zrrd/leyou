package com.leyou.service.pojo.dto.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 保存品牌类.
 *
 * @author shaoyijiong
 * @date 2018/12/4
 */
@Getter
@AllArgsConstructor
public class SaveBrandDto {
  private String name;
  private String image;
  private Character letter;
}
