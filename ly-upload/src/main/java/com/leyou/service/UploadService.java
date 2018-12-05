package com.leyou.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传.
 *
 * @author shaoyijiong
 * @date 2018/12/5
 */
@Slf4j
@Service
public class UploadService {

  private static final List<String> ALLOW_TYPES = Arrays.asList("image/png", "image/jpeg");

  /**
   * 校验并且上传文件.
   */
  public String upload(MultipartFile file) {
    //1.文件校验
    //1.1 校验文件类型
    String type = file.getContentType();
    if (!ALLOW_TYPES.contains(type)) {
      throw new RuntimeException("上传失败，文件类型不匹配");
    }
    //1.2 校验文件数据内容
    BufferedImage img = null;
    try {
      img = ImageIO.read(file.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (img == null) {
      throw new RuntimeException("上传失败图片错误");
    }
    //2. 保存文件到某个位置
    //3. 生成文件地址
    //文件保存
    return null;
  }
}
