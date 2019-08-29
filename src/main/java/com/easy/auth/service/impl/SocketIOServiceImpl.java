package com.easy.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.easy.auth.controller.socket.dto.SocketSendBatchMsgDto;
import com.easy.auth.service.SocketIOService;
import com.easy.auth.utils.returns.Result;
import com.easy.auth.utils.user.UserInfoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * socket 即时通讯消息服务层
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Service(value = "socketIOService")
public class SocketIOServiceImpl implements SocketIOService {
    private static Logger logger = LoggerFactory.getLogger(SocketIOServiceImpl.class);
    /**
     * 存储已连接的客户端
     */
    private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    @Autowired
    private SocketIOServer socketIOServer;

    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     *
     * @throws Exception
     */
    @PostConstruct
    private void autoStartup() throws Exception {
        start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     *
     * @throws Exception
     */
    @PreDestroy
    private void autoStop() throws Exception {
        stop();
    }

    @Override
    public void start() throws Exception {
        // 监听客户端连接
        socketIOServer.addConnectListener(
                client -> {
                    ConcurrentHashMap<String, String> paramsByClient = getParamsByClient(client);
                    if (paramsByClient != null) {
                        String userUniquenessId = paramsByClient.get("userUniquenessId");
                        logger.debug(
                                userUniquenessId
                                        + "连接成功"
                                        + ",SessionId:  "
                                        + client.getSessionId()
                                        + ",RemoteAddress:  "
                                        + client.getRemoteAddress()
                                        + ",Transport:  "
                                        + client.getTransport());
                        clientMap.put(userUniquenessId, client);
                    } else {
                        Result result = new Result();
                        result.setCode(false);
                        result.setMsg("参数有误,请检查");
                        client.sendEvent(PUSH_EVENT, JSONObject.toJSON(result));
                        client.disconnect();
                    }
                });
        // 监听通知事件
        socketIOServer.addEventListener(
                SocketIOService.PUSH_EVENT,
                String.class,
                (client, data, ackRequest) -> {
                    logger.debug("有消息下发:" + data);
                });


        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(
                client -> {
                    ConcurrentHashMap<String, String> paramsByClient = getParamsByClient(client);
                    if (paramsByClient != null) {
                        String userUniquenessId = paramsByClient.get("userUniquenessId");
                        logger.debug(
                                userUniquenessId
                                        + "断开连接"
                                        + ",SessionId:  "
                                        + client.getSessionId()
                                        + ",RemoteAddress:  "
                                        + client.getRemoteAddress()
                                        + ",Transport:  "
                                        + client.getTransport());
                        clientMap.remove(userUniquenessId);
                        client.disconnect();
                    }
                });

        socketIOServer.start();
    }

    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    /**
     * 此方法为获取client连接中的参数，可根据需求更改
     *
     * @param client
     * @return
     */
    private ConcurrentHashMap<String, String> getParamsByClient(SocketIOClient client) {
        logger.info(
                client.getHandshakeData().getSingleUrlParam(UserInfoUtils.USER_UNIQUE_IDENTIFICATION));
        ConcurrentHashMap<String, String> stringStringConcurrentHashMap = new ConcurrentHashMap<>();
        if (StringUtils.isBlank(
                client.getHandshakeData().getSingleUrlParam(UserInfoUtils.USER_UNIQUE_IDENTIFICATION))) {
            return null;
        }
        stringStringConcurrentHashMap.put(
                UserInfoUtils.USER_UNIQUE_IDENTIFICATION,
                client.getHandshakeData().getSingleUrlParam(UserInfoUtils.USER_UNIQUE_IDENTIFICATION));
        return stringStringConcurrentHashMap;
    }

    public static Map<String, SocketIOClient> getClientMap() {
        return clientMap;
    }

    private static void setClientMap(Map<String, SocketIOClient> clientMap) {
        SocketIOServiceImpl.clientMap = clientMap;
    }

    /**
     * 批量发送消息给 在线用户
     *
     * <p>已过时 批量发送消息 请调用 SocketSendController 的 saveMsgLog 方法
     *
     * @param socketSendBatchMsgDto
     * @return
     */
    @Deprecated
    public static Result senMessageToUserByuserUniquenessIdList(
            SocketSendBatchMsgDto socketSendBatchMsgDto) {
        Map<String, SocketIOClient> clientMap = getClientMap();
        Set<String> strings = clientMap.keySet();
        int successCount = 0;
        int failureCount = 0;
        if (strings.size() == 0) {
            return Result.failure("当前无用户登录及时通讯系统!");
        } else {
            for (String one : socketSendBatchMsgDto.getUserUniquenessIdList()) {
                Result result = senMessageToUserByuserUniquenessId(socketSendBatchMsgDto.getMsg(), one);
                if (result.getCode()) {
                    successCount++;
                } else {
                    failureCount++;
                }
            }
            return Result.success(
                    "消息发送完毕,总计发送 "
                            + socketSendBatchMsgDto.getUserUniquenessIdList()
                            + " 条,成功 "
                            + successCount
                            + " 条,失败 "
                            + failureCount
                            + "条");
        }
    }

    /**
     * 单独发送消息给某用户
     *
     * @param msg
     * @param userUniquenessId
     * @return
     */
    public static Result senMessageToUserByuserUniquenessId(String msg, String userUniquenessId) {
        SocketIOClient ioClient = clientMap.get(userUniquenessId);
        if (ioClient != null) {
            try {
                ioClient.sendEvent(SocketIOService.PUSH_EVENT, JSONObject.toJSON(msg));
                return Result.success();
            } catch (Exception e) {
                return Result.failure();
            }
        } else {
            return Result.failure("该用户不在线,本消息已存储到服务器,等待用户上线后,会自动下发本消息给该用户!");
        }
    }

    /**
     * 给当前所有在线的用户发送消息
     *
     * <p>已经过时 请调用 SocketSendController 的 allSenMessage 方法
     *
     * @param msg
     * @return
     */
    @Deprecated
    public static Result sendMessageToAll(String msg) {
        Map<String, SocketIOClient> clientMap = getClientMap();
        int successCount = 0;
        int failureCount = 0;
        if (clientMap.size() == 0) {
            return Result.failure("发送失败,当前即时通讯服务器无人员在线!");
        }
        for (String one : clientMap.keySet()) {
            SocketIOClient socketioclient = clientMap.get(one);
            if (socketioclient != null) {
                try {
                    socketioclient.sendEvent(SocketIOService.PUSH_EVENT, JSONObject.toJSON(msg));
                    successCount++;
                } catch (Exception e) {
                    failureCount++;
                }
            }
        }

        return Result.success(
                "消息发送完毕,总计发送 "
                        + clientMap.size()
                        + " 条,成功 "
                        + successCount
                        + " 条,失败 "
                        + failureCount
                        + "条");
    }
}
