package com.leyou.common.service;

import com.leyou.common.base.exception.ExceptionEnum;
import com.leyou.common.base.exception.LyException;
import com.leyou.common.file.FileService;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

  private final FileService fileService;

  private static final List<String> ALLOW_TYPES = Arrays.asList("image/png", "image/jpeg");

  @Autowired
  public UploadService(FileService fileService) {
    this.fileService = fileService;
  }

  /**
   * 校验并且上传文件.
   */
  public String upload(MultipartFile file) {
    //1.文件校验
    //1.1 校验文件类型
    String type = file.getContentType();
    if (!ALLOW_TYPES.contains(type)) {
      throw new LyException(ExceptionEnum.UPLOAD_MISMATCH);
    }
    //1.2 校验文件数据内容
    BufferedImage img = null;
    try {
      img = ImageIO.read(file.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (img == null) {
      throw new LyException(ExceptionEnum.UPLOAD_IMAGE_ERROR);
    }
    //2. 保存文件到某个位置
    //3. 生成文件地址
    //文件保存
    String url;
    try {
      url = fileService.upload(file.getInputStream(), file.getSize(),
          FilenameUtils.getExtension(file.getOriginalFilename()));
    } catch (IOException e) {
      log.error("文件上传失败:文件名{}", file.getOriginalFilename(), e);
      throw new LyException(ExceptionEnum.UPLOAD_ERROR);
    }

    return url;
  }
}
