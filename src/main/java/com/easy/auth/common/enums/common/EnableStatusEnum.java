package com.easy.auth.common.enums.common;

import com.easy.auth.common.enums.base.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 通用状态枚举类型
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@ApiModel("通用状态枚举类型")
public enum EnableStatusEnum implements BaseEnum<EnableStatusEnum, String> {
  /** */
  @ApiModelProperty("启用/是")
  ENABLE("ENABLE", "启用/是"),
  /** */
  @ApiModelProperty("禁用/否")
  DISABLED("DISABLED", "禁用/否");
  @Getter private String value;
  @Getter private String displayName;

  EnableStatusEnum(String value, String displayName) {
    this.value = value;
    this.displayName = displayName;
  }

  public static EnableStatusEnum getByValue(String value) {
    EnableStatusEnum[] enableStatusEnums = EnableStatusEnum.values();
    for (EnableStatusEnum enableStatusEnum : enableStatusEnums) {
      if (enableStatusEnum.getValue().equals(value)) {
        return enableStatusEnum;
      }
    }
    return null;
  }
}
