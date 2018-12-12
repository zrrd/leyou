package com.leyou.file;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 文件上传实现类.
 *
 * @author shaoyijiong
 * @date 2018/12/7
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

  @Value("${filePrefix}")
  private String filePrefix;

  private final FastFileStorageClient storageClient;

  private final ThumbImageConfig thumbImageConfig;

  @Autowired
  public FileServiceImpl(FastFileStorageClient storageClient, ThumbImageConfig thumbImageConfig) {
    this.storageClient = storageClient;
    this.thumbImageConfig = thumbImageConfig;
  }


  @Override
  public String upload(File file) {
    String ext = FilenameUtils.getExtension(file.getName());
    FileInputStream inputStream;
    try {
      inputStream = FileUtils.openInputStream(file);
    } catch (IOException e) {
      log.error("文件上传失败:文件名{}", file.getName(), e);
      throw new RuntimeException("文件上传失败");
    }
    return upload(inputStream, file.length(), ext);
  }

  @Override
  public String upload(InputStream inputStream, Long fileSize, String ext) {
    StorePath path;
    path = storageClient
        .uploadFile(inputStream, fileSize, ext, null);
    return filePrefix + path.getFullPath();
  }


  @Override
  public Map<String, String> uploadAndCreateThumb(File file) {
    String ext = FilenameUtils.getExtension(file.getName());

    try (FileInputStream stream = new FileInputStream(file)) {
      return uploadAndCreateThumb(stream, file.length(), ext);
    } catch (IOException e) {
      log.error("文件上传失败:文件名{}", file.getName(), e);
      throw new RuntimeException("文件上传失败");
    }
  }

  @Override
  public Map<String, String> uploadAndCreateThumb(InputStream inputStream, Long fileSize,
      String ext) {
    StorePath path = storageClient
        .uploadImageAndCrtThumbImage(inputStream, fileSize, ext, null);
    HashMap<String, String> map = Maps.newHashMapWithExpectedSize(2);
    map.put("imageUrl", filePrefix + path.getFullPath());
    map.put("thumbImageUrl", filePrefix + thumbImageConfig.getThumbImagePath(path.getPath()));
    return map;
  }
}
