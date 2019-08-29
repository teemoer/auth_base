package com.easy.auth.paramconfig;

import com.easy.auth.bean.SystemLog;
import com.easy.auth.dao.SystemLogMapper;
import com.easy.auth.enums.log.SystemLogDescrptionEnum;
import com.easy.auth.utils.ErrorUtils;
import com.easy.auth.utils.IPUtils;
import com.easy.auth.utils.returns.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Date;


/**
 * aop切面 记录 系统敏感接口日志记录
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */

@Aspect
@Component
@Order(-5)
public class SystemLogAspect {

    @Autowired
    private SystemLogMapper systemLogMapper;
    @Autowired
    private SystemLog systemLog;
    @Autowired
    private ErrorUtils errorUtils;

    @Pointcut("@annotation(com.easy.auth.paramconfig.SystemServiceLog)")
    public void serviceAspect() {
    }

    @Pointcut("@annotation(com.easy.auth.paramconfig.SystemControllerLog)")
    public void controllerAspect() {
    }

    /**
     * * 操作日志
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("controllerAspect()")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        if (systemLog.getStartTime() == null) {
            systemLog.setStartTime(new Date(System.currentTimeMillis()));
        }
        Object proceed;

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        systemLog.setActionDate(new Date());
        systemLog.setMobile(request.getHeader("mobile"));
        systemLog.setAdminIdentity(request.getHeader("ident"));
        systemLog.setRequestUrl(request.getRequestURI());
        systemLog.setRequestIp(IPUtils.getIpAddr(request));
        SystemLogDescrptionEnum description = getControllerMethodDescription(joinPoint);
        systemLog.setDescriptions(description.getDisplayName());
        systemLog.setActionMethod(
                joinPoint.getTarget().getClass().getName() + "/" + joinPoint.getSignature().getName());
        systemLog.setActionType(getControllerMethodActionType(joinPoint));

        proceed = joinPoint.proceed();
        if (proceed instanceof Result) {
            Result result = (Result) proceed;
            systemLog.setReturnValue("code:" + result.getCode() + "，msg:" + result.getMsg());
            if (description.getDisplayName().equals(SystemLogDescrptionEnum.删除.getDisplayName())
            ) {
                systemLog.setTargetData(result.getData().toString());
            }

            if (systemLog.getStartTime() != null) {
                BigInteger endTime = BigInteger.valueOf(System.currentTimeMillis());
                BigInteger beginTime = BigInteger.valueOf(systemLog.getStartTime().getTime());
                BigInteger timeCost = (endTime.subtract(beginTime).divide(BigInteger.valueOf(1000)));
                systemLog.setTimeCost(Long.parseLong(timeCost.toString()));
            }

            return result;
        } else {
            Result result = (Result) proceed;
            if (result != null) {
                systemLog.setReturnValue("code:" + result.getCode() + "，msg:" + result.getMsg());
            }
            if (result != null
                    && description.equals(SystemLogDescrptionEnum.删除.getDisplayName())
            ) {
                systemLog.setTargetData(result.getData().toString());
            }

            if (systemLog.getTimeCost() != null) {
                BigInteger endTime = BigInteger.valueOf(System.currentTimeMillis());
                BigInteger beginTime = BigInteger.valueOf(systemLog.getTimeCost());
                BigInteger divide = endTime.subtract(beginTime).divide(BigInteger.valueOf(1000));
                systemLog.setTimeCost(divide.longValue());
            }
            return result;
        }
    }

    @Before("controllerAspect()")
    public void beforLog(JoinPoint joinPoint) throws Throwable {
        if (systemLog.getStartTime() == null) {
            systemLog.setStartTime(new Date(System.currentTimeMillis()));
        }
        String args = argsArrayToString(joinPoint.getArgs());
        systemLog.setParams(args);

        if (args.contains("userName")) {
            String[] split = args.split(",");
            for (String string : split) {
                if (string.contains("userName")) {
                    String replaceAll =
                            string
                                    .replaceAll("userName=", "")
                                    .replaceAll("}", "")
                                    .replaceAll("]", "")
                                    .replaceAll(
                                            "] org.springframework.validation.BeanPropertyBindingResult: 0 errors", "")
                                    .replaceAll("userNames=null", "")
                                    .trim();
                    systemLog.setAdminName(replaceAll);
                    break;
                }
            }
        }
    }

    @After("controllerAspect()")
    public void doAfter() {
        systemLog.setId(null);
        systemLogMapper.inserNewLog(systemLog);
    }

    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 方法
        String method =
                joinPoint.getTarget().getClass().getName() + "/" + joinPoint.getSignature().getName();
        // 参数
        String args = joinPoint.getArgs().toString();

        errorUtils.insertErrorLog(
                request.getHeader("areaCode"),
                1,
                null,
                0,
                "系统日志-AOP异常日志",
                method,
                e.getClass().getName() + "/" + e.getMessage() + "/" + e.getCause(),
                args);
    }

    /**
     * * 获取service的操作信息
     *
     * @param joinpoint
     * @return
     * @throws Exception
     */
    public String getServiceMethodMsg(JoinPoint joinpoint) throws Exception {
        // 获取连接点目标类名
        String className = joinpoint.getTarget().getClass().getName();
        // 获取连接点签名的方法名
        String methodName = joinpoint.getSignature().getName();
        // 获取连接点参数
        Object[] args = joinpoint.getArgs();
        // 根据连接点类的名字获取指定类
        Class<?> targetClass = Class.forName(className);
        // 拿到类里面的方法
        Method[] methods = targetClass.getMethods();

        String description = "";
        // 遍历方法名，找到被调用的方法名
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length) {
                    // 获取注解的说明
                    description = method.getAnnotation(SystemServiceLog.class).decription();
                    break;
                }
            }
        }

        return description;
    }

    /**
     * * 获取controller的操作信息
     *
     * @param point
     * @return
     */
    private SystemLogDescrptionEnum getControllerMethodDescription(ProceedingJoinPoint point) throws Exception {
        // 获取连接点目标类名
        String targetName = point.getTarget().getClass().getName();
        // 获取连接点签名的方法名
        String methodName = point.getSignature().getName();
        // 获取连接点参数
        Object[] args = point.getArgs();
        // 根据连接点类的名字获取指定类
        Class<?> targetClass = Class.forName(targetName);
        // 获取类里面的方法
        Method[] methods = targetClass.getMethods();
        SystemLogDescrptionEnum description = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length) {
                    description = method.getAnnotation(SystemControllerLog.class).descrption();
                    break;
                }
            }
        }
        if (description == null) {
            return SystemLogDescrptionEnum.未定义;
        }
        return description;
    }

    private String getControllerMethodActionType(ProceedingJoinPoint point) throws Exception {
        /*
         * 获取连接点目标类名
         */
        String targetClassName = point.getTarget().getClass().getName();
        /*
         * 获取连接点签名的方法名
         */
        String methodName = point.getSignature().getName();
        /*
         * 获取连接点参数
         */
        Object[] args = point.getArgs();
        /*
         * 根据连接点类的名字获取指定类
         */
        Class<?> targetClass = Class.forName(targetClassName);
        /*
         * 获取类里面的方法
         */
        Method[] methods = targetClass.getMethods();
        String actionType = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length) {
                    actionType = method.getAnnotation(SystemControllerLog.class).actionType();
                    break;
                }
            }
        }

        return actionType;
    }

    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object object : paramsArray) {
                params.append(object.toString()).append(" ");
            }
        }
        return params.toString().trim();
    }
}
