package com.leyou.common.controller;

import com.leyou.common.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("upload")
public class UploadController {


  private final UploadService service;

  @Autowired
  public UploadController(UploadService service) {
    this.service = service;
  }

  /**
   * 上传图片文件.
   */
  @RequestMapping("image")
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
    String url = service.upload(file);
    return ResponseEntity.ok().body(url);
  }
}
