package com.easy.auth.utils;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * ErrorUtils
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
public class ErrorUtils {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ErrorUtils.class);

    /**
     * message 错误信息
     *
     * @author mzw 2019-3-18
     */
    public static void printErrorLog(String message) {
        logger.debug(message);
    }

    /**
     * <p>2.errorLevel错误等级(1严重，2一般，3普通)/
     *
     * <p>3.createUserId创建人Id/
     *
     * <p>4.errorType错误类型(0插入，1修改，2删除， 3其他)/
     *
     * <p>5.modelName模块名称 apiName模块接口/
     *
     * <p>6.errorInfo日志内容/
     *
     * <p>7.remarks 备注/
     *
     * @author mzw
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public long insertErrorLog(
            String code,
            Integer errorLevel,
            String createUserId,
            Integer errorType,
            String modelName,
            String apiName,
            String errorInfo,
            String remarks) {

        return 1L;
    }
}
