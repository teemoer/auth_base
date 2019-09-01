package com.easy.auth.common.task;

import com.easy.auth.bean.SysSocketMsgTaskCache;
import com.easy.auth.common.enums.common.EnableStatusEnum;
import com.easy.auth.infrastructure.config.redis.utils.RedisUtil;
import com.easy.auth.service.SysSocketMsgTaskCacheService;
import com.easy.auth.service.impl.SocketIOServiceImpl;
import com.easy.auth.utils.returns.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 自动获取 即时发送的失败消息 每隔 20S 分钟推送一次
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA. @Data: 13:12
 */
@Component
public class SocketMsgPushTask {
    private static Logger logger = LoggerFactory.getLogger(SocketMsgPushTask.class);

    @Autowired
    SysSocketMsgTaskCacheService sysSocketMsgTaskCacheService;

    @Autowired

    /*
     *
     * 每隔20S执行一次
     *
     * 此处代码有待优化
     *
     * */
    @Scheduled(cron = "0/20 * * * * *")
    public void pushMsg() {
        logger.debug("开始执行task,检测未成功发送的 socket msg任务 ");
        Set<String> onlineUserSet = SocketIOServiceImpl.getClientMap().keySet();
        logger.debug("socket在线用户数量为:" + onlineUserSet.size());
        if (onlineUserSet.size() > 0) {
            int countSendWaitSocketMsg = RedisUtil.countSendWaitSocketMsg();
            if (countSendWaitSocketMsg > 0) {
                List<SysSocketMsgTaskCache> allSysSocketMsgTaskCacheIdList =
                        RedisUtil.getAllSysSocketMsgTaskCacheId();
                allSysSocketMsgTaskCacheIdList.forEach(
                        sysSocketMsgTaskCache -> {
                            try {
                                if (sysSocketMsgTaskCache != null) {
                                    if (onlineUserSet.contains(sysSocketMsgTaskCache.getReceiveUserUniquenessId())) {
                                        Result result =
                                                SocketIOServiceImpl.senMessageToUserByuserUniquenessId(
                                                        sysSocketMsgTaskCache.getMsg(), sysSocketMsgTaskCache.getReceiveUserUniquenessId());
                                        if (result.judgeSuccess()) {
                                            RedisUtil.removeSendWaitSocketMsg(sysSocketMsgTaskCache.getId());
                                            SysSocketMsgTaskCache update = new SysSocketMsgTaskCache();
                                            update.setId(sysSocketMsgTaskCache.getId());
                                            update.setReceiveTime(new Date(System.currentTimeMillis()));
                                            update.setReceiveStatus(EnableStatusEnum.ENABLE);
                                            sysSocketMsgTaskCacheService.updateNotNullById(update);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                logger.error("task 发送socket任务报错", e);
                            }
                        });
            }
        }
    }
}
