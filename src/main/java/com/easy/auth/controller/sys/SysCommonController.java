package com.easy.auth.controller.sys;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统工具方法 控制层
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class SysCommonController {

    /**
     * 获取当前系统所有的 api url信息
     *
     * <p>方便开发人员进行 菜单录入
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAllUrl", method = RequestMethod.POST)
    @ApiOperation("获取当前系统所有的 api url信息 方便开发人员进行 菜单录入")
    public Object init(HttpServletRequest request) {
        List<String> uList = new ArrayList<>();
        /*
         * 获取上下文对象
         */
        WebApplicationContext wac =
                (WebApplicationContext)
                        request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        /*
         * 通过上下文对象获取RequestMappingHandlerMapping实例对象
         */
        RequestMappingHandlerMapping bean = wac.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        for (RequestMappingInfo rmi : handlerMethods.keySet()) {
            PatternsRequestCondition prc = rmi.getPatternsCondition();
            Set<String> patterns = prc.getPatterns();
            uList.addAll(patterns);
        }
        return uList;
    }
}
