package com.easy.auth.common;

import com.easy.auth.enums.common.EnableStatusEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 后台|手机 用户登录 统一父类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description: AdminLoginFormDbDto
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Data
@ApiModel("  SysUser 与 user  的父类")
public class AdminLoginFormDbDto implements Serializable {
    private static final long serialVersionUID = -2531145348908155917L;
    private Integer id;
    private String userName;
    private String password;
    private EnableStatusEnum enableStatus;
    private String uniquenessId;
    private String userTableType;
    private String token;

}
