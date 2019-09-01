package com.easy.auth.common.handler;

import com.easy.auth.bean.PdMenuDict;
import com.easy.auth.common.AdminLoginFormDbDto;
import com.easy.auth.common.exception.UnauthenticatedException;
import com.easy.auth.common.exception.UnknownAccountException;
import com.easy.auth.dao.PdMenuDictMapper;
import com.easy.auth.infrastructure.config.redis.utils.RedisUtil;
import com.easy.auth.service.SysApiWhiteListService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限校验拦截器
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Component
@PropertySource({"classpath:application.properties"})
public class RoleValid extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(RoleValid.class);
    private PdMenuDictMapper pdMenuDictMapper;
    private SysApiWhiteListService sysApiWhiteListService;
    private static Map<String, String> allWhiteUrlListUrlMap = Maps.newHashMap();

    @Value("${system.enable.auth}")
    private Boolean enableAuth = false;

    @Value("${system.info.admin.user.name}")
    private String systemAdminUserName = "";

    public static Map<String, String> getAllWhiteUrlListUrlMap() {
        return allWhiteUrlListUrlMap;
    }

    public static void setAllWhiteUrlListUrlMap(Map<String, String> mapEdited) {
        allWhiteUrlListUrlMap = mapEdited;
    }

    public RoleValid(
            @Autowired PdMenuDictMapper pdMenuDictMapper,
            @Autowired SysApiWhiteListService sysApiWhiteListService) {
        logger.debug("权限验证注入启动,开始初始化权限校验....");
        this.pdMenuDictMapper = pdMenuDictMapper;
        this.sysApiWhiteListService = sysApiWhiteListService;
        allWhiteUrlListUrlMap = sysApiWhiteListService.getAllWhiteUrlList();
    }

    /**
     * 在控制器执行前 执行权限校验
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        String url = request.getRequestURI();
        /*
         *允许全局跨域
         */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /*
         * OPTIONS 请求放行
         */
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        /*
         * 判断访问路径是否为开发api，如果是，直接通过，不进行拦截
         */
        if (StringUtils.isNotBlank(url)) {
            if (!enableAuth) {
                return true;
            } else if (allWhiteUrlListUrlMap.containsKey(url)) {
                /*
                 * url 白名单校验
                 */
                return true;
            } else {

                String token = request.getHeader("token");
                if (token == null || "".equals(token)) {
                    throw new UnknownAccountException("无法获取到token");
                }
                /*
                 * 验证token是否合法
                 */
                Object obj = RedisUtil.getLoginUserInfo(token);
                List<PdMenuDict> menuList = Lists.newArrayList();
                if (obj != null) {

                    /*
                     * 此处操作数据库的方式获取权限关系
                     *
                     * <p>后期可以优化为 从redis获取
                     *
                     * 目前为了快速开发  暂时使用这样的方式
                     */

                    /*
                     * 根据 UniquenessId 获取用户所有权访问的url 进行校验
                     */

                    AdminLoginFormDbDto adminLoginFormDbDto = (AdminLoginFormDbDto) obj;

                    /*
                     *如果是admin账户 就全局放行
                     */
                    if (adminLoginFormDbDto.getUserName().equalsIgnoreCase(systemAdminUserName)) {
                        return true;
                    }

                    /*
                     * 系统开发中 涉及到   对用户的 访问权限进行修改的时候 请务必调用
                     *
                     * RedisUtil.userOperatingSysUserModuleSendUserUniquenessId
                     *
                     * 或
                     * RedisUtil.adminOperatingSysUserModule
                     *
                     * 进行刷新redis权限
                     *
                     */
                    Map<String, String> pathMap =
                            RedisUtil.getMenuListByUserUniquenessId(adminLoginFormDbDto.getUniquenessId());

                    if (pathMap.size() == 0) {
                        menuList = pdMenuDictMapper.findAllMenuByUserUniquenessId(adminLoginFormDbDto.getUniquenessId());
                    } else {
                        throw new UnknownAccountException();
                    }
                    if (menuList.size() > 0) {
                        pathMap =
                                menuList.stream()
                                        .collect(
                                                Collectors.toMap(PdMenuDict::getPath, PdMenuDict::getPath, (v1, v2) -> v1));
                    }

                    if (pathMap.size() > 0) {
                        RedisUtil.setUserUniquenessIdAndMenuList(
                                adminLoginFormDbDto.getUniquenessId(), pathMap);

                        if (StringUtils.isNotBlank(pathMap.get(url))) {
                            return true;
                        }

                        throw new UnauthenticatedException();
                    } else {
                        throw new UnauthenticatedException("该用户暂未配置权限,请联系后台管理员配置!");
                    }
                } else {
                    throw new UnknownAccountException();
                }
            }
        } else {
            return false;
        }
    }

    /**
     * 在后端控制器执行后调用
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView)
            throws Exception {
    }

    /**
     * 整个请求执行完成后调用
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
