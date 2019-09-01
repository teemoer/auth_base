package com.easy.auth.service;

public interface SocketIoService {

    /*
     * 推送的事件
     */
    public static final String PUSH_EVENT = "push_event";

    /*
     * 启动服务
     * @throws Exception
     */
    void start() throws Exception;

    /*
     * 停止服务
     */
    void stop();
}
