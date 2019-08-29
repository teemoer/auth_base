package com.easy.auth.controller.sys;

import com.easy.auth.controller.sys.dto.SysApiWhiteListCo;
import com.easy.auth.bean.SysApiWhiteList;
import com.easy.auth.service.SysApiWhiteListService;
import com.easy.auth.utils.returns.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * api白名单的路由接口服务
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
@RequestMapping("/api/sysApiWhiteList")
@Api(" 系统权限白名单配置 - 控制层")
public class SysApiWhiteListController {

  /** SysConfigService服务 */
  @Autowired private SysApiWhiteListService sysApiWhiteListService;

  /**
   * 分页查询/搜索 白名单列表
   *
   * @param co
   * @return
   */
  @PostMapping("/findByPage")
  @ApiOperation("分页查询/搜索 白名单列表")
  public Result findByPage(@ApiParam(required = true) @RequestBody SysApiWhiteListCo co) {
    return sysApiWhiteListService.findByPage(co);
  }

  /**
   * 新增或修改 白名单
   *
   * @param sysApiWhiteList
   * @return
   */
  @ApiOperation("新增或修改 白名单 - 新增请勿传递id")
  @PostMapping("/save")
  public Result save(@ApiParam(required = true) @RequestBody SysApiWhiteList sysApiWhiteList) {
    return sysApiWhiteListService.saveOrUpdate(sysApiWhiteList);
  }

  /**
   * 根据 ID 删除 白名单
   *
   * @param sysApiWhiteListId
   * @return
   */
  @ApiOperation(value = "根据 ID 删除 白名单", notes = "传值使用url传参方式")
  @PostMapping("/delete")
  @ApiImplicitParam(
      paramType = "query",
      name = "sysApiWhiteListId",
      value = "白名单id值",
      dataType = "long",
      required = true)
  public Result delete(Integer sysApiWhiteListId) {
    return sysApiWhiteListService.deleteById(sysApiWhiteListId);
  }
}
