package com.easy.auth.controller.sys;

import com.easy.auth.bean.SysSocketMsgTaskCache;
import com.easy.auth.utils.page.PageVo;
import com.easy.auth.service.SysSocketMsgTaskCacheService;
import com.easy.auth.utils.returns.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
import springfox.documentation.annotations.ApiIgnore;

/**
 * 发送的即时通讯记录 控制层
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
@RequestMapping("/api/sysSocketMsgTaskCacheController")
@Api(" 发送的即时通讯记录 -控制层")
public class SysSocketMsgTaskCacheController {

    private static Logger logger = LoggerFactory.getLogger(SysSocketMsgTaskCacheController.class);

    /**
     * SysSocketMsgTaskCache Service服务
     */
    @Autowired
    private SysSocketMsgTaskCacheService sysSocketMsgTaskCacheService;

    /**
     * 通过id查询SysSocketMsgTaskCache数据的方法
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取数据", notes = "使用url传参")
    @GetMapping("/findById")
    @ApiImplicitParam(name = "id", paramType = "query", required = true, dataType = "long")
    public Result findById(Long id) {
        return Result.success(sysSocketMsgTaskCacheService.findOne(id));
    }

    /**
     * 通过id删除SysSocketMsgTaskCache数据方法
     *
     * @return
     */
    @ApiOperation(value = "根据ID删除数据", notes = "使用url传参")
    @PostMapping("/deleteById")
    @ApiImplicitParam(name = "id", paramType = "query", required = true, dataType = "long")
    public Result deleteById(Long id) {
        return sysSocketMsgTaskCacheService.deleteById(id);
    }

    @PostMapping("/findListByCondition")
    @ApiOperation(value = "根据条件筛选 获取所有数据", notes = "使用RequestBody传参")
    public Result findAllByCondition(
            @RequestBody @ApiParam(required = true) SysSocketMsgTaskCache beanValue) {
        return sysSocketMsgTaskCacheService.selectListByObj(beanValue);
    }

    /**
     * 增加/修改
     *
     * <p>针对前端屏蔽该接口 发送 和新增 即时通讯 记录接口
     *
     * <p>应该使用 SocketSendController 类中的方法 进行 即时通讯 消息 发送
     *
     * @param beanValues
     * @return
     */
    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "新增或修改", notes = "新增数据请勿携带ID值 修改数据必须携带ID值")
    @ApiIgnore()
    public Result addOrUpdate(
            @RequestBody @ApiParam(required = true) SysSocketMsgTaskCache beanValues) {
        if (beanValues.getId() != null) {
            return sysSocketMsgTaskCacheService.updateNotNullById(beanValues);
        } else {
            return sysSocketMsgTaskCacheService.saveNotNull(beanValues);
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
    public Result findByPage(
            @RequestBody @ApiParam(required = true) PageVo<SysSocketMsgTaskCache> pageBaseVo) {
        return sysSocketMsgTaskCacheService.findByPageVo(pageBaseVo);
    }
}
