package com.easy.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 系统启动完毕  控制台打印提示
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
public class ApplicationRunnerImpl implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(ApplicationRunnerImpl.class);

    @Override
    public void run(ApplicationArguments args) {
        if (logger.isDebugEnabled()) {
            logger.debug("项目启动完毕!");
        } else {
            logger.error("项目启动完毕!");
        }
    }
}
