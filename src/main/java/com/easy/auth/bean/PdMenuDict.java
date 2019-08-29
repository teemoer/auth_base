package com.easy.auth.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * pd_menu_dict实体类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Data
@ApiModel("菜单类 json")
public class PdMenuDict {
  /** 主键Id */
  @ApiModelProperty("主键id - 修改必须传递")
  private Integer id;
  /** 菜单名称 */
  @ApiModelProperty("菜单名称 - 新增必传")
  private String name;
  /** 样式 */
  @ApiModelProperty("样式 ")
  private String styleClass;
  /** 菜单映射地址 */
  @ApiModelProperty("菜单映射地址  - 新增必传")
  private String path;
  /** 是否显示 1 是 2 否 */
  @ApiModelProperty("是否显示 1 是 2 否  - 新增必传")
  private Integer views;
  /** 1.pc 2,用户端 3,快递员 4.司机端 */
  @ApiModelProperty("1.后台 2,管理员端 3,手机端  - 新增必传")
  private Integer type;
  /** 父级菜单Id */
  @ApiModelProperty("父级菜单Id")
  private Integer parentId;
  /** 1 一级菜单 2二级菜单 3 按钮* */
  @ApiModelProperty("1 一级菜单 2二级菜单 3 按钮  - 新增必传")
  private Integer level;
  /** 创建时间 */
  @ApiModelProperty("创建时间")
  private Date creatTime;
  /** 是否是菜单 1 是 0 否 */
  @ApiModelProperty("是否是菜单 1 是 0 否  - 新增必传")
  private Integer isMenu;
  /** 排序字段 值越大优先级越高 */
  @ApiModelProperty("排序字段 值越大优先级越高  - 新增必传")
  private Integer sort;
}
