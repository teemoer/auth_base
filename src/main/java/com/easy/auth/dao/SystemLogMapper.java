package com.easy.auth.dao;

import com.easy.auth.bean.SystemLog;

/**
 * 系统敏感接口访问记录 mapper
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public interface SystemLogMapper {

    void inserNewLog(SystemLog systemLog);
}
