package com.easy.auth.controller.socket;


import com.easy.auth.utils.returns.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * socket 即时通讯 - 连接信息 - 控制层
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
@Api(" socket 即时通讯 - 连接信息  - 控制层")
@RequestMapping("/api/socketInfoController")
public class SocketInfoController {
    private static Integer socketPort;

    public static Integer getSocketPort() {
        return socketPort;
    }

    public static void setSocketPort(Integer socketPort) {
        SocketInfoController.socketPort = socketPort;
    }

    @ApiOperation(value = "获取服务器运行的socket服务运行端口", notes = "无需传参")
    @PostMapping("/getSocketServerPortInfo")
    public Result getSocketServerPortInfo() {
        return Result.success("获取socketServer运行端口成功!", getSocketPort());
    }
}
