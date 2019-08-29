package com.easy.auth.utils.des;

import java.io.IOException;


/**
 * 加密文件工具类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public final class ImageFileTransfer {
  private static final String _fileNameSearchPattern = "*.jpg|*.jepg|*.bmp|*.png|*.gif";
  private static final String fileSearchPattern = "*.doc;*.docx;*.xls;*.xlsx;*.ppt*.pptx|*.pdf";

  /**
   * 下载文件、图片
   *
   * @param dirPath 文件、图片存放目录
   * @param fileName 文件名
   * @return 文件、图片数据(无加密)
   */
  public static byte[] downloadFile(String dirPath, String fileName) throws IOException {
    DES des = new DES();
    String path = dirPath + fileName;
    return des.Decrypt(FileTransfer.downloadFile(path));
  }

  /**
   * 上传文件、图片
   *
   * @param fileName 文件路径
   * @param bytes 文件、图片数据(无加密)
   */
  public static void uploadFile(String fileName, byte[] bytes) throws IOException {
    DES des = new DES();
    FileTransfer.uploadFile(fileName, des.Encrypt(bytes));
  }
}
