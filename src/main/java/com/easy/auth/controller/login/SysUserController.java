package com.easy.auth.controller.login;

import com.easy.auth.bean.SysUser;
import com.easy.auth.service.SysUserService;
import com.easy.auth.utils.page.PageVo;
import com.easy.auth.utils.returns.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户 的路由接口服务
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
@RequestMapping("/SysuserController")
@Api("Sysuser -控制层")
public class SysUserController {

    private static Logger logger = LoggerFactory.getLogger(SysUserController.class);

    /**
     * SysUser Service服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 通过id查询SysUser数据的方法
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取数据", notes = "使用url传参")
    @GetMapping("/findById")
    public Result findById(Integer id) {
        return Result.success(sysUserService.findOne(id));
    }

    /**
     * 通过id删除SysUser数据方法
     *
     * @return
     */
    @ApiOperation(value = "根据ID删除数据", notes = "使用url传参")
    @PostMapping("/deleteById")
    public Result deleteById(@ApiParam(required = true) Integer id) {
        return Result.success(sysUserService.deleteById(id));
    }


    @PostMapping("/findListByCondition")
    @ApiOperation(value = "根据条件筛选 获取所有数据", notes = "使用RequestBody传参")
    public Result findAllByCondition(
            @RequestBody @ApiParam(required = true) SysUser beanValue) {
        return sysUserService.selectListByObj(beanValue);
    }

    /**
     * 增加/修改
     *
     * @param beanValues
     * @return
     */
    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "新增或修改", notes = "新增数据请勿携带ID值 修改数据必须携带ID值")
    public Result addOrUpdate(
            @RequestBody @ApiParam(required = true) SysUser beanValues) {
        if (beanValues.getId() != null) {
            return Result.success(sysUserService.updateNotNullById(beanValues));
        } else {
            return Result.success(sysUserService.saveNotNull(beanValues));
        }
    }

    /**
     * 分页查询
     *
     * @param pageBaseVo
     * @return
     */
    @PostMapping("/findByPage")
    @ApiOperation(value = "分页查询", notes = "使用RequestBody传参")
    public Result findByPage(@RequestBody @ApiParam(required = true) PageVo<SysUser> pageBaseVo) {
        return sysUserService.findByPageVo(pageBaseVo);
    }
}
