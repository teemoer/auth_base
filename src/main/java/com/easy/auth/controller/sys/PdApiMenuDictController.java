package com.easy.auth.controller.sys;

import com.easy.auth.bean.PdMenuDict;
import com.easy.auth.utils.page.PageVo;
import com.easy.auth.service.PdMenuDictService;
import com.easy.auth.utils.returns.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统所有api菜单 - 控制层
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
@RequestMapping("/api/pdMenuDict")
@Api(" 系统所有api菜单 - 控制层")
public class PdApiMenuDictController {
  @Autowired private PdMenuDictService pdMenuDictService;

  /**
   * 新增菜单
   *
   * @param co
   * @return
   */
  @PostMapping("/save")
  @ApiOperation("新增系统菜单api")
  public Result save(@RequestBody PdMenuDict co) {
    return pdMenuDictService.saveNotNull(co);
  }

  @PostMapping("/delete")
  @ApiOperation("根据id删除菜单api")
  public Result delete(Integer id) {
    return pdMenuDictService.deleteById(id);
  }

  @GetMapping("/findAllMenu")
  @ApiOperation("获取所有的菜单api信息")
  @ApiImplicitParam(
          required = false,
          defaultValue = "2",
          dataType = "int",
          value = "isMenu",
          name = "isMenu")
  public Result findAllMenu(@RequestParam(required = false, defaultValue = "2") Integer isMenu) {
    return pdMenuDictService.findAllMenu(isMenu);
  }

  /**
   * 分页查询所有的菜单api
   *
   * @param pageBaseVo
   * @return
   */
  @PostMapping("/findMenuByPage")
  @ApiOperation("分页查询所有的菜单api")
  public Result findMenu(@RequestBody @ApiParam(required = true) PageVo<PdMenuDict> pageBaseVo) {
    return pdMenuDictService.findByPage(pageBaseVo);
  }

  /**
   * 修改菜单api信息 - id值必须传递
   *
   * @param pdMenuDict
   * @return
   */
  @PostMapping("/update")
  @ApiOperation("修改菜单api信息 - id值必须传递")
  public Result update(@RequestBody PdMenuDict pdMenuDict) {
    return pdMenuDictService.updateNotNullById(pdMenuDict);
  }
}
