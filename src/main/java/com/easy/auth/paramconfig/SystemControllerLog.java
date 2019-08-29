package com.easy.auth.paramconfig;

import com.easy.auth.enums.log.SystemLogDescrptionEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  控制层系统敏感日志 注解 类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SystemControllerLog {

    /**
     * 操作的类型
     *
     * <p>操作的类型，1.添加 2.删除 3.修改 4.查询 5.导出 6.打印
     *
     * @return SystemLogEnum
     */
    SystemLogDescrptionEnum descrption() default SystemLogDescrptionEnum.查询;

    /**
     * 操作描述
     *
     * @return
     */
    String actionType() default "";
}
