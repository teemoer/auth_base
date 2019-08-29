package com.easy.auth.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 系统重要接口日志访问记录
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Component
@Data
public class SystemLog {
    private Integer id;

    private String requestUrl;

    private Date actionDate;

    private String actionType;

    private String actionMethod;

    private String requestIp;

    private String params;

    private String descriptions;

    private String returnValue;

    private Long timeCost;

    private String targetData;

    private String adminName;

    private String adminIdentity;

    private String adminAccount;

    private String mobile;

    @ApiModelProperty("临时字段 用于记录日志记录开始时间")
    private Date startTime;

}
