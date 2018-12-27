package com.leyou.common.service.pojo.dto.application;

import lombok.Data;
import lombok.Getter;

/**
 * 保存品牌类.
 *
 * @author shaoyijiong
 * @date 2018/12/4
 */
@Getter
@Data
public class EditBrandDto {

  private Long id;
  private String name;
  private String image;
  private Character letter;
}
