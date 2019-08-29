package com.easy.auth.controller.sys.dto;

import com.easy.auth.bean.SysApiWhiteList;
import lombok.Data;

/**
 * 白名单 Co
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Data
public class SysApiWhiteListCo {
    private int page;
    private int pageSize;
    private SysApiWhiteList sysApiWhiteList = new SysApiWhiteList();
}
