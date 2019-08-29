package com.easy.auth;

/**
 * @Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */

import java.util.Properties;

/**
 * @创建用户: fxb @创建时间: 2017年11月07日 10:00 @描述: myself test demo
 */
public class MyTest {

    public static void main(String[] args) {

        Properties props = System.getProperties();
        System.out.println("操作系统的名称：" + props.getProperty("os.name"));
        System.out.println("操作系统的版本号：" + props.getProperty("os.version"));

    }
}
