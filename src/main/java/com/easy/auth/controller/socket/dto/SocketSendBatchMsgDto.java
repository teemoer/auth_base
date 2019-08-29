package com.easy.auth.controller.socket.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * socket 即时通讯 给批量用户 发送信息 json
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@ApiModel(" socket 即时通讯 给批量用户 发送信息 json")
@Data
public class SocketSendBatchMsgDto extends SendMsgParentDto {

  @ApiModelProperty(value = "需要给那些用户发送 - 用户唯一标识(userUniquenessId) 的数组", required = true)
  private List<String> userUniquenessIdList;

  public SocketSendBatchMsgDto(String msg, List<String> userUniquenessIdList) {
    setMsg(msg);
    setUserUniquenessIdList(userUniquenessIdList);
  }
}
