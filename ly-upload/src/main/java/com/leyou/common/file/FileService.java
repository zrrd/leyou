package com.leyou.common.file;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * 文件上传接口
 *
 * @author shaoyijiong
 * @date 2018/12/7
 */
public interface FileService {

  /**
   * 上传文件
   *
   * @param file 文件
   * @return 文件服务器地址
   */
  String upload(File file);

  /**
   * 通过流上传文件
   *
   * @param inputStream 文件流
   * @param fileSize 文件大小
   * @param ext 文件后缀
   * @return 文件服务器地址
   */
  String upload(InputStream inputStream, Long fileSize, String ext);

  /**
   * 上传文件并生成缩略图
   *
   * @param file 文件
   * @return 文件地址 与 文件缩略图地址  imageUrl表示原图路径  thumbImageUrl表示缩略图路径
   */
  Map<String, String> uploadAndCreateThumb(File file);

  /**
   * 通过流上传文件
   *
   * @param inputStream 文件流
   * @param fileSize 文件大小
   * @param ext 文件后缀
   * @return 文件地址 与 文件缩略图地址  imageUrl表示原图路径  thumbImageUrl表示缩略图路径
   */
  Map<String, String> uploadAndCreateThumb(InputStream inputStream, Long fileSize, String ext);
}
