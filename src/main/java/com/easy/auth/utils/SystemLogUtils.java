package com.easy.auth.utils;

import com.easy.auth.bean.SystemLog;
import com.easy.auth.dao.SystemLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SystemLogUtils
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
public class SystemLogUtils {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
    @Autowired
    private SystemLogMapper systemLogMapper;
    @Autowired
    private ErrorUtils errorUtils;

    public void insertSystemLog(
            String requestUrl,
            String actionType,
            String descriptions,
            String actionMethod,
            String requestIp,
            String params,
            String returnValue,
            String targetData) {

        try {
            SystemLog systemLog = new SystemLog();
            systemLogMapper.inserNewLog(systemLog);

        } catch (Exception e) {
            errorUtils.insertErrorLog(
                    "001",
                    1,
                    null,
                    0,
                    "SystemLogUtils-insertSystemLog1",
                    "SystemLogUtils-insertSystemLog1",
                    e.getMessage() + "/" + e.getCause(),
                    "requestUrl="
                            + requestUrl
                            + ",actionType="
                            + actionType
                            + ",descriptions="
                            + descriptions
                            + ",actionMethod="
                            + actionMethod
                            + ",requestIp="
                            + requestIp
                            + ",params="
                            + params
                            + ",returnValue="
                            + returnValue
                            + ",targetData="
                            + targetData);

            logger.debug("/SystemLogUtils1/" + e);
            e.printStackTrace();
        }
    }

    public void insertSystemLog(SystemLog systemLog) {

        try {

            systemLogMapper.inserNewLog(systemLog);

        } catch (Exception e) {
            errorUtils.insertErrorLog(
                    "001",
                    1,
                    null,
                    0,
                    "SystemLogUtils-insertSystemLog2",
                    "SystemLogUtils-insertSystemLog2",
                    e.getMessage() + "/" + e.getCause(),
                    systemLog.toString());

            logger.debug("/SystemLogUtils2/" + e);
            e.printStackTrace();
        }
    }

    public void insertSystemLog(
            String actionType, String descriptions, String targetData, String adminName, String areaCode) {

        try {


        } catch (Exception e) {
            errorUtils.insertErrorLog(
                    areaCode,
                    1,
                    null,
                    0,
                    "SystemLogUtils-insertSystemLog3",
                    "SystemLogUtils-insertSystemLog3",
                    e.getMessage() + "/" + e.getCause(), null);

            logger.debug("/SystemLogUtils3/" + e);
            e.printStackTrace();
        }
    }
}
