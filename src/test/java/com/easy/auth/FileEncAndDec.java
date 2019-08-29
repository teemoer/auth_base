package com.easy.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileEncAndDec {
  private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FileEncAndDec.class);
  private String Const_Key = "Linyb018";

  private static final int numOfEncAndDec = 0x99; // 加密解密秘钥
  private static int dataOfFile = 0; // 文件字节内容

  public static void main(String[] args) {
    File srcFile = new File("C:\\test\\001.jpg"); // 初始文件
    File encFile = new File("C:\\\\test\\001.jpg"); // 加密文件
    File decFile = new File("C:\\\\test\\003.jpg"); // 解密文件

    try {
      // EncFile(srcFile, encFile); // 加密操作
      DecFile(encFile, decFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 加密
  private static void EncFile(File srcFile, File encFile) throws Exception {
    if (!srcFile.exists()) {
      logger.debug("source file not exixt");
      return;
    }

    if (!encFile.exists()) {
      logger.debug("encrypt file created");
      encFile.createNewFile();
    }
    InputStream fis = new FileInputStream(srcFile);
    OutputStream fos = new FileOutputStream(encFile);

    while ((dataOfFile = fis.read()) > -1) {
      fos.write(dataOfFile ^ numOfEncAndDec);
    }

    fis.close();
    fos.flush();
    fos.close();
  }

  // 解密
  private static void DecFile(File encFile, File decFile) throws Exception {
    if (!encFile.exists()) {
      logger.debug("encrypt file not exixt");
      return;
    }

    if (!decFile.exists()) {
      logger.debug("decrypt file created");
      decFile.createNewFile();
    }

    InputStream fis = new FileInputStream(encFile);
    OutputStream fos = new FileOutputStream(decFile);

    while ((dataOfFile = fis.read()) > -1) {
      fos.write(dataOfFile ^ numOfEncAndDec);
    }

    fis.close();
    fos.flush();
    fos.close();
  }
}
