package com.easy.auth.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * RedisConfig
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
@EnableCaching
@PropertySource({"classpath:application.properties"})
public class RedisConfig extends CachingConfigurerSupport {

  static class LocalConfiguration {
    // 从application.properties中获得以下参数
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.default-expiration}")
    private long defaultExpiration;

    @Bean
    public RedisTemplate<Serializable, Serializable> redisTemplate(
        JedisConnectionFactory redisConnectionFactory) {
      RedisTemplate<Serializable, Serializable> redisTemplate =
          new RedisTemplate<Serializable, Serializable>();
      // key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
      // 所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现
      // ObjectRedisSerializer
      // 或者JdkSerializationRedisSerializer序列化方式;
      redisTemplate.setKeySerializer(new StringRedisSerializer());
      redisTemplate.setHashKeySerializer(new StringRedisSerializer());
      redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
      redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
      // 以上4条配置可以不用
      redisTemplate.setConnectionFactory(redisConnectionFactory);
      return redisTemplate;
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
      JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
      jedisPoolConfig.setMaxIdle(maxIdle);
      jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
      JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
      redisConnectionFactory.setHostName(host);
      redisConnectionFactory.setPort(port);
      // redisConnectionFactory.setPassword(password);
      redisConnectionFactory.setPoolConfig(jedisPoolConfig);
      return redisConnectionFactory;
    }
  }

  /** 自定义key. 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key */
  @Override
  public KeyGenerator keyGenerator() {
    return new KeyGenerator() {
      @Override
      public Object generate(Object o, Method method, Object... objects) {
        StringBuilder sb = new StringBuilder();
        sb.append(o.getClass().getName());
        sb.append(method.getName());
        for (Object obj : objects) {
          sb.append(obj.toString());
        }
        return sb.toString();
      }
    };
  }
}
