package com.easy.auth.enums.user;

import com.easy.auth.enums.base.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;

/**
 * 当前登录用户 类型  目前登录接口 支持 sysUser 和 user 两个表的用户登录
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@ApiModel(" 当前登录用户 类型  目前登录接口 支持 sysUser 和 user 两个表的用户登录")
public enum UserTableTypeEnum implements BaseEnum<UserTableTypeEnum, String>, Serializable {

  /** */
  @ApiModelProperty(value = "管理员用户", notes = "")
  SYS_USER("SYS_USER", "管理员用户"),
  /**
   *
   */
  @ApiModelProperty(value = "普通用户", notes = "")
  USER("USER", "普通用户");

  @Getter private String value;
  @Getter private String displayName;

  UserTableTypeEnum(String value, String displayName) {
    this.value = value;
    this.displayName = displayName;
  }

  public static UserTableTypeEnum getByValue(String value) {
    UserTableTypeEnum[] userTableTypeEnums = UserTableTypeEnum.values();
    for (UserTableTypeEnum userTableTypeEnum : userTableTypeEnums) {
      if (userTableTypeEnum.getValue().equals(value)) {
        return userTableTypeEnum;
      }
    }
    return null;
  }
}
