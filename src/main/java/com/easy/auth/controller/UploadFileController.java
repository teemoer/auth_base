package com.easy.auth.controller;

import com.easy.auth.common.config.ConfigMessage;
import com.easy.auth.common.enhance.MyCustomizeStringList;
import com.easy.auth.utils.FileUtil;
import com.easy.auth.utils.des.ImageFileTransfer;
import com.easy.auth.utils.returns.Result;
import com.easy.auth.utils.user.UserInfoUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文件上传和下载 的 加密与解密 路由接口服务
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UploadFileController {
    @Autowired
    ConfigMessage configMessage;
    private static Logger logger = Logger.getLogger(UploadFileController.class);


    private static Map<String, MyCustomizeStringList> tokenFileNameListMap = Maps.newHashMap();

    /**
     * 根据当前登录用户的token 信息 获取该用户已上文件列表
     *
     * @return
     */
    public static MyCustomizeStringList getThisUserUploadFileNameListByToken() {
        String token = UserInfoUtils.getThisLoginUserToken();
        MyCustomizeStringList fileNameList = tokenFileNameListMap.get(token);
        if (fileNameList == null) {
            return new MyCustomizeStringList();
        } else {
            tokenFileNameListMap.remove(token);
            return fileNameList;
        }
    }

    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result singleUpload(
            @RequestParam(value = "file", required = false) MultipartFile file) {
        String token = UserInfoUtils.getThisLoginUserToken();
        if (file.isEmpty()) {
            return Result.fail("附件不存在");
        }
        String saveFilePath =
                getFilePathByFileNameAndModuleNameAndAreaCode(
                        file.getOriginalFilename());
        String saveFileName = null;
        try {
            saveFileName = createDirIfNotExistAndModifyFileName(saveFilePath, file.getOriginalFilename());
            /*
             * 上传未加密文件
             */
            file.transferTo(new File(saveFilePath + saveFileName));
            byte[] bytes = Files.readAllBytes(Paths.get(saveFilePath + saveFileName));
            ImageFileTransfer.uploadFile(saveFilePath + saveFileName, bytes);
            MyCustomizeStringList fileNameList = tokenFileNameListMap.get(token);
            if (fileNameList == null) {
                fileNameList = new MyCustomizeStringList();
                fileNameList.add(saveFileName);
                tokenFileNameListMap.put(token, fileNameList);
            } else {
                fileNameList.add(saveFileName);
                tokenFileNameListMap.put(token, fileNameList);
            }
        } catch (Exception e) {
            logger.error("上传文件报错：", e);
            // 上传失败，删除文件
            File delFile = new File(saveFilePath + saveFileName);
            delFile.delete();
            return Result.fail("上传失败");
        }
        return Result.success("上传成功", saveFileName);
    }

    /**
     * 多文件上传
     */
    @RequestMapping(value = "/batchUpload", method = RequestMethod.POST)
    @ResponseBody
    public Result batchUpload(
            @RequestParam(value = "files", required = false) MultipartFile[] files) {
        String token = UserInfoUtils.getThisLoginUserToken();
        if ((files == null) || (files.length == 0)) {
            return Result.fail("附件不存在");
        }
        List<Map> listMap = Lists.newArrayList();

        for (MultipartFile file : files) {
            Map<String, Object> maps = Maps.newHashMap();
            maps.put("uploadFileName", file.getOriginalFilename());
            maps.put("failureMsg", null);
            String saveFilePath =
                    getFilePathByFileNameAndModuleNameAndAreaCode(
                            file.getOriginalFilename());
            String saveFileName =
                    createDirIfNotExistAndModifyFileName(saveFilePath, file.getOriginalFilename());
            maps.put("serverSaveFileName", saveFileName);
            try {
                /*
                 * 上传未加密文件
                 */
                file.transferTo(new File(saveFilePath + saveFileName));
                byte[] bytes = Files.readAllBytes(Paths.get(saveFilePath + saveFileName));
                ImageFileTransfer.uploadFile(saveFilePath + saveFileName, bytes);
                MyCustomizeStringList fileNameList = tokenFileNameListMap.get(token);
                if (fileNameList == null) {
                    fileNameList = new MyCustomizeStringList();
                    fileNameList.add(saveFileName);
                    tokenFileNameListMap.put(token, fileNameList);
                } else {
                    fileNameList.add(saveFileName);
                    tokenFileNameListMap.put(token, fileNameList);
                }
                maps.put("success", true);

            } catch (Exception e) {
                maps.put("failureMsg", "上传该文件失败:" + e.getMessage());
                maps.put("success", false);
            } finally {
                listMap.add(maps);
            }
        }

        return Result.success("批量上传完毕", listMap);
    }

    /**
     * 多文件下载
     *
     * <p>自动根据文件扩展名判断文件的所属路径
     *
     * @param fileNameList
     * @return
     */
    @PostMapping("/downLoadFileList")
    public Result downLoadFileList(@RequestBody String[] fileNameList) {
        List<Map> filePaths = new ArrayList<>();

        for (String oneFileName : fileNameList) {
            if (StringUtils.isBlank(oneFileName)) {
                continue;
            }
            String thisFilePath =
                    getFilePathByFileNameAndModuleNameAndAreaCode(
                            oneFileName);
            try {
                byte[] bytes = ImageFileTransfer.downloadFile(thisFilePath, oneFileName);
                BASE64Encoder base64Encoder = new BASE64Encoder();
                String encode = base64Encoder.encode(bytes);
                encode = encode.replaceAll("[\\s*\t\n\r]", "");
                Map map1 = new HashMap(2);
                map1.put("fileBase64Code", encode);
                map1.put("fileName", oneFileName);
                filePaths.add(map1);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.fail("读取文件失败");
            }
        }
        return Result.success("下载成功", filePaths);
    }

    /**
     * 下载读取文件图片(单图片单文件)
     *
     * @param
     * @param
     * @param
     * @return
     */
    @PostMapping("/downloadFile")
    public Result downloadFile(@RequestBody String fileName) {
        Map outMap = new HashMap(1);
        String thisFilePath =
                getFilePathByFileNameAndModuleNameAndAreaCode(
                        fileName);
        if (fileName != null) {
            File file = new File(fileName, thisFilePath);
            if (!file.exists()) {
                try {
                    byte[] bytes = ImageFileTransfer.downloadFile(thisFilePath, fileName);
                    if (bytes != null) {
                        BASE64Encoder base64Encoder = new BASE64Encoder();
                        String encode = base64Encoder.encode(bytes);
                        encode = encode.replaceAll("[\\s*\t\n\r]", "");
                        outMap.put("fileBase64Code", encode);
                        outMap.put("fileName", fileName);
                    } else {
                        return Result.fail("文件读取失败!");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return Result.fail("读取文件失败");
                }
            }
        }

        return Result.success("下载成功", outMap);
    }

    /**
     * 删除文件
     *
     * @return
     */
    @PostMapping("/deleteFile")
    public Result deleteFile(@RequestBody String fileName) {
        String thisFilePath =
                getFilePathByFileNameAndModuleNameAndAreaCode(
                        fileName);

        File file = new File(thisFilePath + fileName);
        if (file.exists() && file.isFile()) {
            boolean delete = file.delete();
            if (delete) {
                return Result.success(ConfigMessage.DELETE_SUCCESS);
            } else {
                return Result.failure("删除文件失败");
            }

        } else {
            return Result.failure("文件不存在");
        }
    }

    /**
     * 重命名文件
     *
     * <p>判断目录是否存在 如果不存在 即
     *
     * @param filePath 文件地址
     * @param fileName 文件名称
     * @return 返回新的文件名称为 原文件名称.年月日时间戳.文件名后缀
     */
    private String createDirIfNotExistAndModifyFileName(String filePath, String fileName) {
        if (StringUtils.isNotEmpty(filePath)) {
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                boolean mkdirs = fileDir.mkdirs();
                if (mkdirs) {
                    logger.error("创建文件夹 " + filePath + " 失败,请检查服务器是否有该路径下的控制权限!");
                }
            }
        }
        SimpleDateFormat fmdate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fileName.substring(0, fileName.lastIndexOf("."))
                + "."
                + fmdate.format(new Date())
                + "."
                + fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * 根据 文件名 和 moduleName 和 areaCode 获取文件实际存储路径
     *
     * @param fileName
     * @return
     */
    private String getFilePathByFileNameAndModuleNameAndAreaCode(
            String fileName) {
        String saveFilePath;
        if (FileUtil.isImage(fileName)) {
            saveFilePath = configMessage.getImagePath();
        } else if (FileUtil.isXlsx(fileName)) {
            saveFilePath = configMessage.getXlsxPath();
        } else {
            saveFilePath = configMessage.getFilePath();
        }
        return saveFilePath;
    }


    @PostMapping("/reade")
    public Result reade(String imagePath) {
        try {
            File file = new File(imagePath);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                String line;
                StringBuilder str = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    str.append(line);
                }
                br.close();
                isr.close();
                fis.close();
                return Result.success("读取成功", str.toString());
            } else {
                return Result.fail("文件路径不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail(ConfigMessage.NODATA_MESSAGE);
        }
    }
}
