package com.easy.auth.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色与菜单关系 实体类
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
@ApiModel(" 角色与菜单关系 json")
public class RoleMenuRel {
    @ApiModelProperty(value = "", notes = "")
    private Integer id;
    @ApiModelProperty(value = "对应 role_info 表的主键id", notes = "")
    private Integer roleId;
    @ApiModelProperty(value = "角色名称", notes = "")
    private String roleName;
    @ApiModelProperty(value = "关联菜单id", notes = "")
    private Integer menuId;
    @ApiModelProperty(value = "创建该角色的用户的用户名", notes = "")
    private String createByUserName;
    @ApiModelProperty(value = "创建记录时间", notes = "")
    private java.util.Date createDate;
}
