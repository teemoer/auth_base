package com.easy.auth.utils.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.easy.auth.common.AdminLoginFormDbDto;
import com.easy.auth.infrastructure.config.redis.utils.RedisUtil;
import com.easy.auth.service.impl.SysUserServiceImpl;
import com.easy.auth.utils.servlet.RequestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息工具类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public class UserInfoUtils {

    private static RequestUtils requestUtils;
    private static Logger logger = LoggerFactory.getLogger(UserInfoUtils.class);
    /**
     * 用户唯一标示的key
     */
    public static final String USER_UNIQUE_IDENTIFICATION = "userUniquenessId";

    private static SysUserServiceImpl sysUserService;

    /**
     * 根据当前用户 唯一id 和 md5密码生成token
     *
     * @param uUserUniquenessId
     * @param uPasswordMd5
     * @return
     */
    public static String getToken(String uUserUniquenessId, String uPasswordMd5) {
        return JWT.create()
                .withAudience(uUserUniquenessId)
                .sign(Algorithm.HMAC256(uPasswordMd5 + RandomStringUtils.randomAlphanumeric(10)));
    }

    /**
     * 获取当前登陆用户
     *
     * <p>调用本方法 不需要判断 获取到 的用户是否为 null
     *
     * <p>如果 为null 本方法会抛出异常 由异常管理器统一处理 自动阻止代码往下运行
     *
     * @return
     */
    public static AdminLoginFormDbDto getUser() {
        String token = getThisLoginUserToken();
        // 验证token是否合法
        Object obj = RedisUtil.getLoginUserInfo(token);
        if (obj != null) {
            return (AdminLoginFormDbDto) obj;
        } else {
            throw new UnknownAccountException("redis无法找到该token,可能是用户未登录或登录信息已过期,请尝试退出重新登录!");
        }
    }

    /**
     * 从 http Header 或 http path Query 获取 当前请求信息的token
     *
     * @return
     */
    public static String getThisLoginUserToken() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("token");
        token = StringUtils.isBlank(token) ? request.getParameter("token") : token;
        if (StringUtils.isBlank(token)) {
            throw new UnknownAccountException("http Header 与 http path Query 参数中 都未携带token ,无法获取用户信息!");
        } else {
            return token;
        }
    }
}
