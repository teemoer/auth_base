package com.easy.auth.common.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 消息提示相关
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Component
@PropertySource({"classpath:application.properties"})
@Data
public class ConfigMessage {
    private static Logger logger = LoggerFactory.getLogger(ConfigMessage.class);
  public static final String INSERT_SUCCESS = "保存数据成功";
  public static final String INSERT_FAILED = "保存数据失败";

  public static final String UPDATE_SUCCESS = "更新数据成功";
  public static final String UPDATE_FAILED = "更新数据失败";

  public static final String DELETE_SUCCESS = "删除成功";
  public static final String DELETE_FAILED = "删除失败";

  public static final String QUERY_SUCCESS = "查询数据成功";
  public static final String QUERY_FAILED = "查询数据失败";

  public static final String NODATA_MESSAGE = "暂无相关数据";
  public static final String DATA_EXISTENCE = "数据已存在";



  public static final String DATA_NOT_EXIST = "删除失败,记录不存在";



  public static final String PARAMETER_NOT_NULL = "参数不能为空";
  public static final String PARAM_ERROR = "参数不能为空";

  public static final String USER_ISNULL = "用户信息不能为空";

  public static final String DATA_ID_BLANK = "请使提供数据ID";

  public static final String USER_EXISTS = "用户已存在";

  public static final int FILE_MAX_SIZE = 20480;
  public static final int IMAGE_MAX_SIZE = 2048;
  public static final String FILE_TOO_BIG = "过大的文件";
  public static final String UPLOAD_ERROR = "上传异常";
  public static final String FILE_TYPE_ERR = "不支持的文件类型";

    private static Properties properties;
    private static String serverOs = "windows";

    static {
        try {
            properties = System.getProperties();
            String property = properties.getProperty("os.name");
            if (StringUtils.isNotBlank(serverOs)) {
                if (property.toLowerCase().contains("mac")) {
                    serverOs = "mac";
                } else {
                    serverOs = "windows";
                }
            }
        } catch (Exception e) {
            serverOs = "windows";
            logger.error("初始化下载文件配置类,获取当前操作系统信息报错,默认设置本服务器操作系统为windwos!", e);
        }
    }

    @Value("${server.upload.windows.os.file.path}")
  private String filePath;

    @Value("${server.upload.windows.os.xlsx.path}")
  private String xlsxPath;

    @Value("${server.upload.windows.os.image.path}")
    private String imagePath;

    /**
     * 读取配置中本地模板文件路径
     */
    @Value("${excel.windows.os.excel.moban}")
    private String excelMoBanPath;

    @Value("${server.upload.mac.os.file.path}")
    private String macOsFilePath;

    @Value("${server.upload.mac.os.xlsx.path}")
    private String macOsXlsxPath;

    @Value("${server.upload.mac.os.image.path}")
    private String macOsImagePath;

    @Value("${excel.mac.os.excel.moban}")
    private String macOsExcelMoBanPath;

    private String getMacOsFilePath() {
        return macOsFilePath;
    }

    private String getMacOsXlsxPath() {
        return macOsXlsxPath;
    }

    private String getMacOsImagePath() {
        return macOsImagePath;
    }

    private String getMacOsExcelMoBanPath() {
        return macOsExcelMoBanPath;
    }

    public String getExcelMoBanPath() {
        if ("mac".equalsIgnoreCase(serverOs) && StringUtils.isNotBlank(this.macOsExcelMoBanPath)) {
            return this.macOsExcelMoBanPath;
        }
        return excelMoBanPath;
    }

    public String getFilePath() {
        if ("mac".equalsIgnoreCase(serverOs) && StringUtils.isNotBlank(this.macOsFilePath)) {
            return this.macOsFilePath;
        }
        return filePath;
    }

    public String getXlsxPath() {
        if ("mac".equalsIgnoreCase(serverOs) && StringUtils.isNotBlank(this.macOsXlsxPath)) {
            return this.macOsXlsxPath;
        }
        return xlsxPath;
    }

    public String getImagePath() {
        if ("mac".equalsIgnoreCase(serverOs) && StringUtils.isNotBlank(this.macOsImagePath)) {
            return this.macOsImagePath;
        }
        return imagePath;
    }


  public static final String LOGOUT_SUCCESS = "注销成功";
  public static final String USE_USER = "该账号已被停用，不能登录";
}
