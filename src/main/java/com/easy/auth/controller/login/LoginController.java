package com.easy.auth.controller.login;

import com.easy.auth.bean.SysUser;
import com.easy.auth.common.AdminLoginDto;
import com.easy.auth.common.AdminLoginFormDbDto;
import com.easy.auth.common.SysConstans;
import com.easy.auth.enums.common.EnableStatusEnum;
import com.easy.auth.enums.user.UserTableTypeEnum;
import com.easy.auth.infrastructure.config.redis.utils.RedisUtil;
import com.easy.auth.paramconfig.ConfigMessage;
import com.easy.auth.service.SysUserService;
import com.easy.auth.service.UserService;
import com.easy.auth.utils.GetErrors;
import com.easy.auth.utils.returns.Result;
import com.easy.auth.utils.user.UserInfoUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登录 控制层
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping(value = "/api/common")
@PropertySource({"classpath:application.properties"})
@Api("用户登录 - 控制层")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserService userService;


    /**
     * 登录接口
     *
     * <p>双用户登录的写法
     *
     * <p>
     *
     * @param adminLoginDto
     * @param bResult
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("  后台  登录接口")
    public Result adminLogin(
            @RequestBody @Valid @ApiParam(required = true) AdminLoginDto adminLoginDto,
            BindingResult bResult) {
        if (bResult.hasErrors()) {
            return GetErrors.getErrorMsg(bResult);
        }

        AdminLoginFormDbDto adminLoginFormDbDto =
                sysUserService.selectOneByUserName(adminLoginDto.getUserName());
        /*
         * 如果查询不到 sysUser  尝试 查找 user 表
         */
        if (adminLoginFormDbDto == null) {
            adminLoginFormDbDto = userService.selectOneByUserName(adminLoginDto.getUserName());
        }

        /*
         * 开始检查 用户信息合法性
         */
        if (adminLoginFormDbDto == null) {
            return Result.fail("登录失败,该用户不存在!");
        } else if (StringUtils.isBlank(adminLoginFormDbDto.getUniquenessId())) {
            return Result.fail("登录失败,该用户暂未分配唯一标示,请联系开发人员!");
        } else if (EnableStatusEnum.DISABLED.getValue().equals(adminLoginFormDbDto.getEnableStatus().getValue())) {
            return Result.failure(ConfigMessage.USE_USER);
        }



        /*
         *检测密码一致 为登录成功  存储用户信息到redis 并返回唯一token
         */
        if (adminLoginFormDbDto.getPassword().equalsIgnoreCase(adminLoginDto.getPassword())) {

            String jwt =
                    UserInfoUtils.getToken(
                            adminLoginFormDbDto.getUniquenessId(), adminLoginFormDbDto.getPassword());

            Map<String, Object> outLoginInfoMap = new HashMap(6);
            outLoginInfoMap.put("token", jwt);
            adminLoginFormDbDto.setToken(jwt);
            List menuList = null;
            /*
             *控制json输出 密码为 null
             */
            adminLoginFormDbDto.setPassword(null);
            if (adminLoginFormDbDto instanceof SysUser) {
                adminLoginFormDbDto.setUserTableType(UserTableTypeEnum.SYS_USER.getValue());
                /*
                 * 管理员用户
                 */
                menuList = sysUserService.findUserModelListByUniquenessId(adminLoginFormDbDto.getUniquenessId());
            } else {
                adminLoginFormDbDto.setUserTableType(UserTableTypeEnum.USER.getValue());
                /*
                 * 普通用户
                 */
                menuList = userService.findUserModelListByUniquenessId(adminLoginFormDbDto.getUniquenessId());
            }
            outLoginInfoMap.put("user", adminLoginFormDbDto);
            outLoginInfoMap.put("menuList", menuList);
            RedisUtil.addLoginInfo(jwt, adminLoginFormDbDto, SysConstans.AUTH_TIME_OUT);
            return Result.success(outLoginInfoMap);
        } else {
            return Result.fail("登录失败,用户或密码错误");
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("用户退出系统")
    public Result logout(HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            if (StringUtils.isNotBlank(token)) {
                RedisUtil.del(token);
            }
        } catch (Exception e) {
            logger.error("用户登出失败:", e);
        }

        return Result.success(ConfigMessage.LOGOUT_SUCCESS);
    }
}
