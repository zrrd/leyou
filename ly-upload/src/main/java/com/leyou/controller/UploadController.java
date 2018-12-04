package com.leyou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上次图片文件.
 *
 * @author 邵益炯
 * @date 2018/12/4
 */
@RestController
@RequestMapping("upload")
public class UploadController {

  @RequestMapping("image")
  public String upload(MultipartFile file) {
    return null;
  }
}
