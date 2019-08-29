package com.easy.auth.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 系统api白名单
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
@ApiModel(" 系统白名单 json ")
public class SysApiWhiteList {
    /**
     * 主键Id
     */
    @ApiModelProperty("主键id - 新增勿传")
    private Integer id;
    /**
     * 系统名称
     */
    @ApiModelProperty("api名称")
    private String apiName;
    /**
     * api url 地址
     */
    @ApiModelProperty("api url 地址")
    private String apiUrl;
    /**
     * 备注信息
     */
    @ApiModelProperty("备注信息")
    private String mark;
    /**
     * 创建时间
     */
    //    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;
}
