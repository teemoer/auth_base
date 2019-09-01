package com.easy.auth.common.enums.log;

import com.easy.auth.common.enums.base.BaseEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 类功能描述
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA. @Data: 09:33
 */
public enum SystemLogDescrptionEnum implements BaseEnum<SystemLogDescrptionEnum, String> {

    /**
     *
     */
    @ApiModelProperty(value = "添加", notes = "")
    添加("添加", "添加"),
    /**
     *
     */
    @ApiModelProperty(value = "删除", notes = "")
    删除("删除", "删除"),
    /**
     *
     */
    @ApiModelProperty(value = "修改", notes = "")
    修改("修改", "修改"),
    /**
     *
     */
    @ApiModelProperty(value = "查询", notes = "")
    查询("查询", "查询"),
    /**
     *
     */
    @ApiModelProperty(value = "导出", notes = "")
    导出("导出", "导出"),
    /**
     *
     */
    @ApiModelProperty(value = "添加", notes = "")
    打印("打印", "打印"),
    /**
     *
     */
    @ApiModelProperty(value = "导出", notes = "")
    未定义("未定义", "未定义");

    @Getter
    private String value;
    @Getter
    private String displayName;

    SystemLogDescrptionEnum(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public static SystemLogDescrptionEnum getByValue(String value) {
        SystemLogDescrptionEnum[] systemLogDescrptionEnums = SystemLogDescrptionEnum.values();
        for (SystemLogDescrptionEnum systemLogDescrptionEnum : systemLogDescrptionEnums) {
            if (systemLogDescrptionEnum.getValue().equals(value)) {
                return systemLogDescrptionEnum;
            }
        }
        return null;
    }
}
