package com.easy.auth.common.socket;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.easy.auth.controller.socket.SocketInfoController;
import com.easy.auth.utils.PortUtil;
import com.easy.auth.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * socket 配置类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Configuration
public class SocketIoConfig {
    private static Logger logger = LoggerFactory.getLogger(SocketIoConfig.class);
    @Value("${socketio.host}")
    private String host;

    @Value("${socketio.port}")
    private Integer port;

    @Value("${socketio.bossCount}")
    private int bossCount;

    @Value("${socketio.workCount}")
    private int workCount;

    @Value("${socketio.allowCustomRequests}")
    private boolean allowCustomRequests;

    @Value("${socketio.upgradeTimeout}")
    private int upgradeTimeout;

    @Value("${socketio.pingTimeout}")
    private int pingTimeout;

    @Value("${socketio.pingInterval}")
    private int pingInterval;

    /**
     * 以下配置在上面的application.properties中已经注明
     *
     * @return
     */
    @Bean(name = "mySocketIOServer")
    public SocketIOServer socketIOServer() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        socketConfig.setReuseAddress(true);
        com.corundumstudio.socketio.Configuration config =
                new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname(host);
        config.setPort(port);
        config.setBossThreads(bossCount);
        config.setWorkerThreads(workCount);
        config.setAllowCustomRequests(allowCustomRequests);
        config.setUpgradeTimeout(upgradeTimeout);
        config.setPingTimeout(pingTimeout);
        config.setPingInterval(pingInterval);
        SocketIOServer socketIOServer;
        if (PortUtil.isLocalePortUsing(config.getPort())) {
            config.setPort(port + RandomUtils.randomSpecifiedLengthNumber(3));
            logger.warn("项目启动检测到socket端口:" + port + "被占用,现已重新随机设置socket监听端口为:" + config.getPort() + "");
        }
        socketIOServer = new SocketIOServer(config);
        SocketInfoController.setSocketPort(config.getPort());
        return socketIOServer;

    }
}
