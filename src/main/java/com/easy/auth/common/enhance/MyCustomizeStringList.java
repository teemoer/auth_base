package com.easy.auth.common.enhance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * list增强类 增加 getFileNameStr方法
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA. @Data: 13:50
 */
public class MyCustomizeStringList extends ArrayList<String> {
    private static Logger logger = LoggerFactory.getLogger(MyCustomizeStringList.class);

    /**
     * @return
     */
    public String getFileNameStr() {
        return MyCustomizeListUtils.getFileNameStr(this);
    }
}
