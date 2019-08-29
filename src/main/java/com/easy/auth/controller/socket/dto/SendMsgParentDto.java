package com.easy.auth.controller.socket.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 类功能描述
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@ApiModel(" socket 即时通讯    发送信息 父类 json")
@Data
public class SendMsgParentDto {
  @ApiModelProperty(value = "需要给用户发送的消息内容", required = true)
  @NotNull(message = "需要发送的消息必须不为空")
  private String msg;
}
