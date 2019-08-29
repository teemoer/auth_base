package com.easy.auth.common.enhance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * list 工具类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA. @Data: 13:13
 */
public class MyCustomizeListUtils {
  private static Logger logger = LoggerFactory.getLogger(MyCustomizeListUtils.class);

  /**
   * 获取 map中的value 如果 数量超过1 个 用 逗号 [,] 做分割
   *
   * @return
   */
  static String getFileNameStr(List<String> fileNameList) {
    StringBuilder filelNameStringBuilder = new StringBuilder();
    if (fileNameList.size() == 0) {
      return filelNameStringBuilder.toString();
    } else {
      for (int i = 0; i < fileNameList.size(); i++) {
        if (i == 0) {
          filelNameStringBuilder = new StringBuilder(fileNameList.get(i));
        } else {
          filelNameStringBuilder.append(",").append(fileNameList.get(i));
        }
      }
      return filelNameStringBuilder.toString();
    }
  }
}
