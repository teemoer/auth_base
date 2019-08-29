package com.easy.auth.utils;

import java.io.File;

/**
 * 文件处理工具类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public class FileUtil {
  /**
   * 是否是图片
   *
   * @param fileName
   * @return
   */
  public static boolean isImage(String fileName) {
    String type = getFileSuffix(fileName);
    type = type.toLowerCase();
    return "jpg".equals(type) || "jpeg".equals(type) || "png".equals(type) || "gif".equals(type);
  }

  /**
   * 获取一个新的文件名
   *
   * @param fileName
   * @return
   */
  public static String getNewFileName(String fileName) {
    String type = getFileSuffix(fileName);
    return System.currentTimeMillis() + "." + type;
  }

  /**
   * 根据文件名称获取文件后缀名
   *
   * @param fileName
   * @return
   */
  private static String getFileSuffix(String fileName) {
    fileName = fileName.toLowerCase().substring(fileName.lastIndexOf(".") + 1);
    return fileName;
  }

  /**
   * 判断是否是xlsx/xls/docx/doc/zip之一
   *
   * @param fileName
   * @return
   */
  public static boolean isXlsx(String fileName) {
    String type = getFileSuffix(fileName);
    return "xlsx".equals(type)
            || "xls".equals(type)
            || "docx".equals(type)
            || "doc".equals(type)
            || "zip".equals(type)
            || "txt".equals(type);
  }

  /**
   * 删除文件
   *
   * @param files
   * @return
   */
  public static boolean dropFile(String files) {
    File file = new File(files);
    return file.delete();
  }

  /**
   * @author ao
   * @param fileName
   * @return 取文件后缀
   */
  public static String getFileType(String fileName) {
    return getFileSuffix(fileName);
  }

  /**
   * @author ao
   * @param areaCode
   * @param module 判断文件路径是否存在
   */
  public static Boolean checkPath(String uploadPath, String areaCode, String module) {

    String filePath = uploadPath + "/" + module + "/" + areaCode;
    File file = new File(filePath);

    if (!file.exists()) {
      return file.mkdirs();
    }

    return true;
  }

  /**
   * @author ao
   * @param filePath
   * @return 判断文件是否存在
   */
  public static Boolean checkFile(String filePath) {

    File file = new File(filePath);

    return file.isDirectory();
  }

  /**
   * @author ao
   * @param fileName
   * @return 判断文件类型
   */
  public static Boolean isImageFile(String fileName) {
    String[] imgType = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp", ".psd"};
    if (fileName == null) {
      return false;
    }
    fileName = fileName.toLowerCase();
    for (String type : imgType) {
      if (fileName.endsWith(type)) {
        return true;
      }
    }
    return false;
  }
}
