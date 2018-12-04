package com.leyou.controller;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上次图片文件.
 *
 * @author 邵益炯
 * @date 2018/12/4
 */
@Slf4j
@RestController
@RequestMapping("upload")
public class UploadController {

  private static final List<String> SUFFIXES = Arrays.asList("image/png", "image/jpeg");

  /**
   * 上次图片文件.
   */
  @RequestMapping("image")
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
    //1.文件校验
    //1.1 校验文件类型
    String type = file.getContentType();
    if (!SUFFIXES.contains(type)) {
      throw new RuntimeException("上传失败，文件类型不匹配");
    }
    //1.2 校验文件数据内容
    //文件保存
    return null;
  }
}
