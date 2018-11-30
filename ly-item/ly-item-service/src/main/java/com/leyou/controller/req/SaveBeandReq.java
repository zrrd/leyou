package com.leyou.controller.req;

import lombok.Data;

/**
 * .
 * @author shaoyijong
 * @date 2018/11/30
 */
@Data
public class SaveBeandReq {
  private Long id;
  private String name;
  private String image;
  private Character letter;
}
