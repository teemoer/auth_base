package com.easy.auth.bean;

import com.easy.auth.enums.common.EnableStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 发送的即时通讯记录
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
@ApiModel(" 发送的即时通讯记录 json")
public class SysSocketMsgTaskCache implements Serializable {
    private static final long serialVersionUID = 6022198373630178667L;
    @ApiModelProperty(value = "主键id", notes = "")
    private Long id;

    @ApiModelProperty(value = "消息接收用户的用户名", notes = "")
    private String receiveUserName;

    @ApiModelProperty(value = "消息接收用户的姓名", notes = "")
    private String receiveName;

    @ApiModelProperty(value = "消息接收用户唯一标识", notes = "")
    private String receiveUserUniquenessId;

    @ApiModelProperty(value = "用户接收时间", notes = "")
    private java.util.Date receiveTime;

    @ApiModelProperty(value = "消息发送用户的用户名", notes = "")
    private String sendUserUserName;
    @ApiModelProperty(value = "发送消息内容", notes = "")
    private String msg;

    @ApiModelProperty(value = "消息发送用户的姓名", notes = "")
    private String sendName;

    @ApiModelProperty(value = "消息发送时间", notes = "")
    private java.util.Date sendTime;

    @ApiModelProperty(value = "消息接收用户是否已阅读 或 是否发送成功 - [ENABLE 已读],[ DISABLED 未读] ", notes = "")
    private EnableStatusEnum receiveStatus;

    public SysSocketMsgTaskCache(String receiveUserName, String receiveName,
                                 String receiveUserUniquenessId, Date receiveTime,
                                 String sendUserUserName, String sendName,
                                 Date sendTime, EnableStatusEnum receiveStatus, String msg) {
        this.receiveUserName = receiveUserName;
        this.receiveName = receiveName;
        this.receiveUserUniquenessId = receiveUserUniquenessId;
        this.receiveTime = receiveTime;
        this.sendUserUserName = sendUserUserName;
        this.sendName = sendName;
        this.sendTime = sendTime;
        this.receiveStatus = receiveStatus;
        this.msg = msg;
    }

    public SysSocketMsgTaskCache() {
    }
}
