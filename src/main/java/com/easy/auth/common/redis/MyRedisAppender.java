package com.easy.auth.common.redis;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import com.cwbase.logback.AdditionalField;
import com.cwbase.logback.JSONEventLayout;
import com.cwbase.logback.RedisAppender;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 复写 RedisAppender 实现 日志服务器redis 连接不上 不会影响服务器运行速度
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA. @Data: 15:37
 */
public class MyRedisAppender extends RedisAppender {
    private static Logger logger = LoggerFactory.getLogger(MyRedisAppender.class);

    private static JedisPool pool;

    private static JSONEventLayout jsonlayout;

    Layout<ILoggingEvent> layout;
    private static Boolean isConnectionError = Boolean.FALSE;

    String host = "localhost";
    int port = Protocol.DEFAULT_PORT;
    String key = null;
    int timeout = Protocol.DEFAULT_TIMEOUT;
    String password = null;
    int database = Protocol.DEFAULT_DATABASE;

    public MyRedisAppender() {
        if (jsonlayout == null) {
            jsonlayout = new JSONEventLayout();
        }
    }

    @Override
    protected void append(ILoggingEvent event) {
        String json = layout == null ? jsonlayout.doLayout(event) : layout.doLayout(event);
        if (pool == null || pool.isClosed()) {
            System.out.println("日志服务器连接失败,已自动关闭,无法再向日志服务器推送消息C,当前推送消息为:\n" + json);
        } else {
            Jedis client = pool.getResource();
            try {
                client.rpush(key, json);
            } catch (Exception e) {
                e.printStackTrace();
                pool.returnBrokenResource(client);
                client = null;
            } finally {
                if (client != null) {
                    pool.returnResource(client);
                }
            }
        }

    }

    @Override
    @Deprecated
    public String getSource() {
        return jsonlayout.getSource();
    }

    @Override
    @Deprecated
    public void setSource(String source) {
        jsonlayout.setSource(source);
    }

    @Override
    @Deprecated
    public String getSourceHost() {
        return jsonlayout.getSourceHost();
    }

    @Override
    @Deprecated
    public void setSourceHost(String sourceHost) {
        jsonlayout.setSourceHost(sourceHost);
    }

    @Override
    @Deprecated
    public String getSourcePath() {
        return jsonlayout.getSourcePath();
    }

    @Override
    @Deprecated
    public void setSourcePath(String sourcePath) {
        jsonlayout.setSourcePath(sourcePath);
    }

    @Override
    @Deprecated
    public String getTags() {
        if (jsonlayout.getTags() != null) {
            Iterator<String> i = jsonlayout.getTags().iterator();
            StringBuilder sb = new StringBuilder();
            while (i.hasNext()) {
                sb.append(i.next());
                if (i.hasNext()) {
                    sb.append(',');
                }
            }
            return sb.toString();
        }
        return null;
    }

    @Override
    @Deprecated
    public void setTags(String tags) {
        if (tags != null) {
            String[] atags = tags.split(",");
            jsonlayout.setTags(Arrays.asList(atags));
        }
    }

    @Override
    @Deprecated
    public String getType() {
        return jsonlayout.getType();
    }

    @Override
    @Deprecated
    public void setType(String type) {
        jsonlayout.setType(type);
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    @Override
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getDatabase() {
        return database;
    }

    @Override
    public void setDatabase(int database) {
        this.database = database;
    }

    @Override
    @Deprecated
    public void addAdditionalField(AdditionalField p) {
        jsonlayout.addAdditionalField(p);
    }

    @Override
    public Layout<ILoggingEvent> getLayout() {
        return layout;
    }

    @Override
    public void setLayout(Layout<ILoggingEvent> layout) {
        this.layout = layout;
    }

    @Override
    public void start() {
        this.started = true;
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setTestOnBorrow(true);
        if (isConnectionError) {
            System.out.println("日志服务器连接失败,已自动关闭,无法再向日志服务器推送消息A");
        }
        try {
            if (pool == null) {
                pool = new JedisPool(config, host, port, timeout, password, database);
            } else if (pool.isClosed()) {
                pool = new JedisPool(config, host, port, timeout, password, database);
            }
        } catch (Exception e) {
            isConnectionError = Boolean.TRUE;
            System.out.println("日志服务器连接失败,已自动关闭,无法再向日志服务器推送消息B");
        }


    }

    @Override
    public void stop() {
        this.started = false;
//    pool.destroy();
    }
}
