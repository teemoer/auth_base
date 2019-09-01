package com.easy.auth.controller.socket;

import com.easy.auth.bean.SysSocketMsgTaskCache;
import com.easy.auth.common.AdminLoginFormDbDto;
import com.easy.auth.common.enums.common.EnableStatusEnum;
import com.easy.auth.controller.socket.dto.SendMsgParentDto;
import com.easy.auth.controller.socket.dto.SocketSendBatchMsgDto;
import com.easy.auth.controller.socket.dto.SocketSingleSendMsgDto;
import com.easy.auth.infrastructure.config.redis.utils.RedisUtil;
import com.easy.auth.service.SysSocketMsgTaskCacheService;
import com.easy.auth.service.SysUserService;
import com.easy.auth.service.impl.SocketIOServiceImpl;
import com.easy.auth.utils.GetErrors;
import com.easy.auth.utils.returns.Result;
import com.easy.auth.utils.user.UserInfoUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * socket 即时通讯 - 控制层
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@RestController
@Api(" socket 即时通讯  - 控制层")
@RequestMapping("/api/socketSendController")
public class SocketSendController {

  @Autowired
  SysUserService sysUserService;
  @Autowired
  SysSocketMsgTaskCacheService sysSocketMsgTaskCacheService;

  /**
   * 统计在线总数
   *
   * @return
   */
  @GetMapping("/getOnlineList")
  public Result getOnlineList() {
    return Result.success(SocketIOServiceImpl.getClientMap().keySet());
  }

  /**
   * 给指定的批量用户发送 即时信息
   *
   * @param socketSendBatchMsgDto
   * @return
   */
  @ApiOperation(value = " 给指定的批量用户发送 即时信息", notes = "")
  @PostMapping("/batch/send/message")
  public Result batchSendMessage(
          @RequestBody @ApiParam(required = true) SocketSendBatchMsgDto socketSendBatchMsgDto) {
    return saveMsgLog(socketSendBatchMsgDto);
  }

  @ApiOperation(value = "给指定的areaCode 下 并且 拥有 modeChineseName 的用户发送消息 ", notes = "全部使用url传参")
  @PostMapping("/by/areaCode/and/modeChineseName/send/message")
  public Result sendMessageByAreaCodeAndModeChineseName(
          String areaCode, String modeChineseName, String msg) {
    List<String> userUniquenessIdList =
            sysSocketMsgTaskCacheService.findUserUniquenessIdByAreaCodeAndModelName(
                    areaCode, modeChineseName);
    if (userUniquenessIdList.size() == 0) {
      return Result.failure("找不到消息接收对象!");
    }
    return saveMsgLog(new SocketSendBatchMsgDto(msg, userUniquenessIdList));
  }
  /**
   * 给指定的单个用户发送 即时信息
   *
   * <p>并 记录到 即时消息发送记录中
   *
   * @param socketSingleSendMsgDto
   * @return
   */
  @ApiOperation(" 给指定的单个用户发送 即时信息")
  @PostMapping("/single/send/message")
  public Result singleSendMessage(
          @Valid @RequestBody @ApiParam(required = true) SocketSingleSendMsgDto socketSingleSendMsgDto,
          BindingResult bResult) {
    if (bResult.hasErrors()) {
      return GetErrors.getErrorMsg(bResult);
    }
    String userUniquenessId = socketSingleSendMsgDto.getUserUniquenessId();
    AdminLoginFormDbDto adminLoginFormDbDto =
            sysUserService.getSysUserOrUserByUserUniquenessId(userUniquenessId);
    if (adminLoginFormDbDto == null) {
      return Result.fail("消息接收对象唯一标示为:" + userUniquenessId + " 的用户并不存在!");
    }

    return saveMsgLog(socketSingleSendMsgDto);
  }

  @ApiOperation(value = "给所有在线的用户发送即时消息", notes = "调用本接口只需要传递 msg 即可")
  @PostMapping("/all/send/message")
  public Result allSenMessage(
          @RequestBody @ApiParam(required = true) SocketSendBatchMsgDto socketSendBatchMsgDto) {

    List<String> onlineUserUniquenessIdList =
            new ArrayList<>(SocketIOServiceImpl.getClientMap().keySet());
    socketSendBatchMsgDto.setUserUniquenessIdList(onlineUserUniquenessIdList);
    return saveMsgLog(socketSendBatchMsgDto);
  }

  /**
   * 发送消息 给用户 并且 记录到 即时通讯记录 中
   *
   * @param sendMsgParentDto
   * @return
   */
  private Result saveMsgLog(SendMsgParentDto sendMsgParentDto) {
    AdminLoginFormDbDto nowLoginUser = UserInfoUtils.getUser();

    if (sendMsgParentDto instanceof SocketSingleSendMsgDto) {
      /*
       *如果针对单个用户发送
       */
      SocketSingleSendMsgDto socketSingleSendMsgDto = (SocketSingleSendMsgDto) sendMsgParentDto;
      String userUniquenessId = socketSingleSendMsgDto.getUserUniquenessId();
      AdminLoginFormDbDto receiveUserInfo =
              sysUserService.getSysUserOrUserByUserUniquenessId(userUniquenessId);

      SysSocketMsgTaskCache sysSocketMsgTaskCache =
              new SysSocketMsgTaskCache(
                      receiveUserInfo.getUserName(),
                      receiveUserInfo.getUserName(),
                      receiveUserInfo.getUniquenessId(),
                      null,
                      nowLoginUser.getUserName(),
                      nowLoginUser.getUserName(),
                      new Date(System.currentTimeMillis()),
                      EnableStatusEnum.DISABLED,
                      socketSingleSendMsgDto.getMsg());

      Result result =
              SocketIOServiceImpl.senMessageToUserByuserUniquenessId(
                      socketSingleSendMsgDto.getMsg(), socketSingleSendMsgDto.getUserUniquenessId());

      if (result.judgeSuccess()) {
        sysSocketMsgTaskCache.setReceiveStatus(EnableStatusEnum.ENABLE);
        sysSocketMsgTaskCache.setReceiveTime(new Date(System.currentTimeMillis()));
        sysSocketMsgTaskCacheService.saveNotNull(sysSocketMsgTaskCache);
      } else {
        sysSocketMsgTaskCacheService.saveNotNull(sysSocketMsgTaskCache);
        RedisUtil.addSendWaitSocketMsg(sysSocketMsgTaskCache);
      }

      return result;
    } else {

      /*
       *如果是 批量发送
       */
      SocketSendBatchMsgDto socketSendBatchMsgDto = (SocketSendBatchMsgDto) sendMsgParentDto;

      List<String> userUniquenessIdList = socketSendBatchMsgDto.getUserUniquenessIdList();
      if (userUniquenessIdList.size() == 0) {
        return Result.fail("消息接收对象 userUniquenessIdList 列表 不能为空!");
      }

      final int[] successCount = {0};
      final int[] failureCount = {0};
      userUniquenessIdList.forEach(
              one -> {
                AdminLoginFormDbDto receiveUserInfo = sysUserService.getSysUserOrUserByUserUniquenessId(one);
                if (receiveUserInfo == null) {
                  failureCount[0]++;
                } else {
                  SysSocketMsgTaskCache sysSocketMsgTaskCache =
                          new SysSocketMsgTaskCache(
                                  receiveUserInfo.getUserName(),
                                  receiveUserInfo.getUserName(),
                                  receiveUserInfo.getUniquenessId(),
                                  null,
                                  nowLoginUser.getUserName(),
                                  nowLoginUser.getUserName(),
                                  new Date(System.currentTimeMillis()),
                                  EnableStatusEnum.DISABLED,
                                  socketSendBatchMsgDto.getMsg());

                  Result result =
                          SocketIOServiceImpl.senMessageToUserByuserUniquenessId(
                                  socketSendBatchMsgDto.getMsg(), receiveUserInfo.getUniquenessId());

                  if (result.judgeSuccess()) {
                    sysSocketMsgTaskCache.setReceiveStatus(EnableStatusEnum.ENABLE);
                    sysSocketMsgTaskCache.setReceiveTime(new Date(System.currentTimeMillis()));
                    successCount[0]++;
                  } else {
                    sysSocketMsgTaskCacheService.saveNotNull(sysSocketMsgTaskCache);
                    failureCount[0]++;
                    RedisUtil.addSendWaitSocketMsg(sysSocketMsgTaskCache);
                  }
                }
              });

      return Result.success(
              "消息发送完毕,总计发送 "
                      + socketSendBatchMsgDto.getUserUniquenessIdList()
                      + " 条,成功 "
                      + successCount[0]
                      + " 条,失败 "
                      + failureCount[0]
                      + "条");
    }
  }
}
