package com.easy.auth.utils.des;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
final class FileTransfer {
    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @return 文件byte数据
     */
    static byte[] downloadFile(String filePath) throws IOException {
        if (Files.exists(Paths.get(filePath))) {
            return Files.readAllBytes(Paths.get(filePath));
        } else {
            return null;
        }
    }

    /**
     * 上传文件
     *
     * @param filePath 文件路径
     * @param bytes    数据
     */
    static void uploadFile(String filePath, byte[] bytes) throws IOException {
        FileOutputStream out = new FileOutputStream(filePath);
        out.write(bytes);
        out.close();
    }
}
