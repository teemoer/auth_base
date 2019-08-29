package com.easy.auth.infrastructure.config.redis.utils;

import com.easy.auth.common.AdminLoginFormDbDto;
import com.easy.auth.bean.SysSocketMsgTaskCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 工具类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public class RedisUtil {
  private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
  private static JedisPool jedisPool = null;

  /**
   * 常量值中的 冒号 是用来做分组的 不可删除
   *
   * <p>否则会导致 报错
   */
  private static final String USER_UUID_AND_PATH_API_KEY_START = "userUuidAndPathApiArr--:";

  private static final String SOCKET_SEND_MSG = "socketSendMsgArr--:";
  private static final String USER_LOGIN_INGO = "userLoginInfoArr--:";

  public static String JEDIS_IP = JavaUtil.getPropertiesValueByKey("spring.redis.host").trim();
  public static int JEDIS_PORT =
      Integer.parseInt(JavaUtil.getPropertiesValueByKey("spring.redis.port").trim());
  public static String JEDIS_PWD = JavaUtil.getPropertiesValueByKey("spring.redis.password").trim();

  // 初始化redis连接池
  static {
    JedisPoolConfig config = new JedisPoolConfig();
    // 设置最大连接数 可以创建3000jedis实例
    config.setMaxTotal(
        Integer.parseInt(JavaUtil.getPropertiesValueByKey("spring.redis.jedis.pool.max-active")));
    // 设置最大空闲连接数
    config.setMaxIdle(
        Integer.parseInt(JavaUtil.getPropertiesValueByKey("spring.redis.jedis.pool.max-idle")));
    // 等待可用连接的最大时间
    config.setMaxWaitMillis(
        Integer.parseInt(JavaUtil.getPropertiesValueByKey("spring.redis.jedis.pool.max-wait")));
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
    config.setTestOnBorrow(true);
    jedisPool =
        new JedisPool(
            config,
            JEDIS_IP,
            JEDIS_PORT,
            Integer.parseInt(JavaUtil.getPropertiesValueByKey("spring.redis.timeout")),
            JEDIS_PWD);
  }

  // 连接
  public static synchronized Jedis createRedis() {
    if (jedisPool != null) {
      Jedis resource = jedisPool.getResource();
      return resource;
    } else {
      return null;
    }
  }

  /**
   * 序列化
   *
   * @param object
   * @return
   */
  public static byte[] serialize(Object object) {
    ObjectOutputStream oos = null;
    ByteArrayOutputStream baos = null;
    try {
      baos = new ByteArrayOutputStream();
      oos = new ObjectOutputStream(baos);
      oos.writeObject(object);
      byte[] bytes = baos.toByteArray();
      return bytes;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 反序列化
   *
   * @param bytes
   * @return
   */
  public static Object unSerialize(byte[] bytes) {
    ByteArrayInputStream bais = null;
    try {
      bais = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = new ObjectInputStream(bais);
      return ois.readObject();
    } catch (Exception e) {
      return null;
    }
  }

  // 向redis中添加对象
  public static void setObjectRedis(String key, Object object) {
    Jedis jedis = null;
    try {
      jedis = createRedis();
      jedis.set(key.getBytes(), serialize(object));
    } finally {
      jedis.close();
    }
  }
  // 向redis中添加对象
  public static void setObjectRedis(String key, Object object, int seconds) {
    Jedis jedis = null;
    try {
      jedis = createRedis();
      jedis.set(key.getBytes(), serialize(object));
      if (seconds != 0) {
        jedis.expire(key.getBytes(), seconds);
      }
    } catch (Exception e) {
      logger.error("连接redis报错:", e);
    } finally {
      jedis.close();
    }
  }

  // 向redis中添加对象
  public static void setEffectiveObjectRedis(String key, Object object, Long secondTime) {
    Jedis jedis = null;
    try {
      jedis = createRedis();
      if (secondTime == null) {
        secondTime = 600L;
      }
      boolean keyExist = jedis.exists(key);
      if (keyExist) {
        jedis.del(key);
      }
      // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
      jedis.set(key.getBytes(), serialize(object), "NX".getBytes(), "EX".getBytes(), secondTime);
    } finally {
      jedis.close();
    }
  }

  // 向redis中添加对象
  public static void setEffectiveObjectRedis(Object key, Object object, Long secondTime) {
    Jedis jedis = null;
    try {
      jedis = createRedis();
      if (secondTime == null) {
        secondTime = 600L;
      }
      boolean keyExist = jedis.exists(serialize(key));
      if (keyExist) {
        jedis.del(serialize(key));
      }
      // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
      jedis.set(serialize(key), serialize(object), "NX".getBytes(), "EX".getBytes(), secondTime);
    } finally {
      jedis.close();
    }
  }

  public static void setObjectRedis(Object key, Object object) {
    Jedis jedis = null;
    try {
      jedis = createRedis();
      jedis.set(serialize(key), serialize(object));
    } finally {
      jedis.close();
    }
  }

  // 从redis中获取对象
  public static Object getObjectRedis(String key) {
    Object object = null;
    Jedis jedis = null;
    try {
      jedis = createRedis();
      if (jedis.get(key.getBytes()) != null) {
        object = unSerialize(jedis.get(key.getBytes()));
      }
    } finally {
      jedis.close();
    }
    return object;
  }

  public static Object getObjectRedis(Object key) {
    Object object = null;
    Jedis jedis = null;
    try {
      jedis = createRedis();
      if (jedis.get(serialize(key)) != null) {
        object = unSerialize(jedis.get(serialize(key)));
      }
    } finally {
      jedis.close();
    }
    return object;
  }

  // 向redis中存放字符串
  public static void setStringRedis(String key, String value) {

    Jedis jedis = null;
    try {
      jedis = createRedis();
      jedis.set(key, value);

    } finally {
      jedis.close();
    }
  }

  // 向redis中存放字符串
  public static void setStringRedis(String key, String value, int seconds) {

    Jedis jedis = null;
    try {
      jedis = createRedis();
      jedis.set(key, value);
      if (seconds != 0) {
        jedis.expire(key.getBytes(), seconds);
      }

    } finally {
      jedis.close();
    }
  }
  // 从redis中获取字符串
  public static String getStringRedis(String key) {

    Jedis jedis = null;
    String value = null;
    try {
      jedis = createRedis();
      value = jedis.get(key);
    } finally {
      jedis.close();
    }
    return value;
  }

  public static void setMapRedis(String key, Map map) {

    Jedis jedis = null;
    try {
      jedis = createRedis();
      jedis.hmset(key, map);
    } finally {
      jedis.close();
    }
  }

  /**
   * 删除
   *
   * @param key
   */
  public static void del(String key) {
    Jedis jedis = null;
    String value = null;
    try {
      jedis = createRedis();

        if (key.contains(":*")) {
            Set<String> keys = jedis.keys(key);
            List<String> keyList = new ArrayList<>(keys);

            for (String s : keyList) {
                jedis.del(s);
            }

        } else {
            jedis.del(key);
        }

    } finally {
      jedis.close();
    }
  }

  public static int countSizeByGroupName(String groupingName) {
    Jedis jedis = null;
    String value = null;
    try {
      jedis = createRedis();
      Set<String> keys = jedis.keys(groupingName + "*");
      return keys.size();
    } catch (Exception e) {
      return 0;
    } finally {
      jedis.close();
    }
  }

  /**
   * 删除
   *
   * @param key
   */
  public static void del(Object key) {
    Jedis jedis = null;
    String value = null;
    try {
      jedis = createRedis();
      jedis.del(serialize(key));
    } finally {
      jedis.close();
    }
  }

  /**
   * 设置失效时间
   *
   * @param key
   * @param timeout
   */
  public static void setExp(String key, Long timeout) {

    Jedis jedis = null;
    String value = null;
    try {
      jedis = createRedis();
      jedis.pexpire(key, timeout);
    } finally {
      jedis.close();
    }
  }

  /**
   * 存储 userUniquenessId 和 pathList
   *
   * @param userUniquenessId
   * @param map
   */
  public static void setUserUniquenessIdAndMenuList(
          String userUniquenessId, Map<String, String> map) {
    if (map.size() > 0) {
      setObjectRedis(USER_UUID_AND_PATH_API_KEY_START + userUniquenessId, map);
    }
  }

  /**
   * 根据 userUniquenessId 获取 pathList
   *
   * @param userUniquenessId
   * @return
   */
  public static Map<String, String> getMenuListByUserUniquenessId(String userUniquenessId) {
    Object objectRedis = getObjectRedis(USER_UUID_AND_PATH_API_KEY_START + userUniquenessId);
    if (objectRedis == null) {
      return Maps.newHashMap();
    } else {
      return (Map<String, String>) objectRedis;
    }
  }

  /**
   * 当 管理员 涉及到 针对该下属 的 普通用户 的模块或功能修改操作的时候
   *
   * <p>调用本方法刷新 该普通用户的权限
   *
   * <p>userUniquenessId 为被操作的普通用户的唯一标识符
   *
   * @param userUniquenessId
   */
  public static void userOperatingSysUserModuleSendUserUniquenessId(
          String userUniquenessId) {
    del(USER_UUID_AND_PATH_API_KEY_START + userUniquenessId);
  }

  /**
   * 当 最高管理员 涉及到 针对 用户 的模块或功能修改操作
   *
   * <p>和
   *
   * <p>当 最高管理员 涉及到 针对 系统模块或者模块的功能 进行修改的时候
   *
   * <p>调用本方法刷新 所有用户的权限
   */
  public static void adminOperatingSysUserModule() {
    del(USER_UUID_AND_PATH_API_KEY_START + "*");
  }

  /**
   * 根据 sysSocketMsgTaskCacheId 添加
   *
   * @param sysSocketMsgTaskCache
   */
  public static void addSendWaitSocketMsg(SysSocketMsgTaskCache sysSocketMsgTaskCache) {
    setObjectRedis(SOCKET_SEND_MSG + sysSocketMsgTaskCache.getId(), sysSocketMsgTaskCache);
  }

  /**
   * 根据 sysSocketMsgTaskCacheId 删除
   *
   * @param sysSocketMsgTaskCacheId
   */
  public static void removeSendWaitSocketMsg(Long sysSocketMsgTaskCacheId) {
    del(SOCKET_SEND_MSG + sysSocketMsgTaskCacheId);
  }

  /**
   * 获取所有等待下发的 即时通讯消息
   *
   * @return
   */
  public static int countSendWaitSocketMsg() {
    return countSizeByGroupName(SOCKET_SEND_MSG);
  }

  public static List<SysSocketMsgTaskCache> getAllSysSocketMsgTaskCacheId() {

    Jedis jedis = null;
    List<SysSocketMsgTaskCache> allSysSocketMsgTaskCacheIdList = Lists.newArrayList();
    try {
      jedis = createRedis();
      Set<String> keys = jedis.keys(SOCKET_SEND_MSG + "*");
      if (keys.size() != 0) {
        Jedis finalJedis = jedis;
        keys.forEach(
                one -> {
                    SysSocketMsgTaskCache sysSocketMsgTaskCache;
                    Object o = unSerialize(finalJedis.get(one.getBytes()));
                    if (o != null) {
                        sysSocketMsgTaskCache = (SysSocketMsgTaskCache) o;
                        allSysSocketMsgTaskCacheIdList.add(sysSocketMsgTaskCache);
                    }
                });
      }

    } finally {
      if (jedis != null && jedis.isConnected()) {
        jedis.close();
      }
      return allSysSocketMsgTaskCacheIdList;
    }
  }

  public static void addLoginInfo(
          String jwt, AdminLoginFormDbDto adminLoginFormDbDto, int authTimeOut) {
    setObjectRedis(USER_LOGIN_INGO + jwt, adminLoginFormDbDto, authTimeOut);
  }

  public static Object getLoginUserInfo(String token) {
    return getObjectRedis(USER_LOGIN_INGO + token);
  }
}
