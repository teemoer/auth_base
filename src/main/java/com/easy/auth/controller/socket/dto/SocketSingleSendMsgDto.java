package com.easy.auth.controller.socket.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * socket 即时通讯 针对单个 发送信息 json
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@ApiModel(" socket 即时通讯 针对单个 发送信息 json")
@Data
public class SocketSingleSendMsgDto extends SendMsgParentDto {

  @NotNull(message = "需要发送的 用户唯一标识(userUniquenessId) 不能为空")
  @ApiModelProperty(value = "需要给单个用户发送 - 用户唯一标识", required = true)
  private String userUniquenessId;

  public SocketSingleSendMsgDto(String msg, String userUniquenessId) {
    setMsg(msg);
    setUserUniquenessId(userUniquenessId);
  }

  public SocketSingleSendMsgDto() {
  }
}
