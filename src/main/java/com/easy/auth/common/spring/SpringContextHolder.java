package com.easy.auth.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * SpringContextTools
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public class SpringContextHolder {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBeanByClass(Class<T> requiredType) {
        Assert.notNull(applicationContext);
        return applicationContext.getBean(requiredType);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBeanByNameAndClass(String beanName, Class<T> requiredType) {
        Assert.notNull(applicationContext);
        return applicationContext.getBean(beanName, requiredType);
    }
}
