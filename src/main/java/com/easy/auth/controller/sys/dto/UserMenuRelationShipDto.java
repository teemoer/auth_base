package com.easy.auth.controller.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户与菜单关系  - 用户菜单权限关系 json
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@ApiModel(" 用户与菜单关系  - 用户菜单权限关系 json")
@Data
public class UserMenuRelationShipDto {
  @ApiModelProperty("UserMenuRelationShip关系的 主键id - 新增勿传 - 修改必传")
  private Long id;

  @ApiModelProperty("菜单表id")
  private Long menuId;

  @ApiModelProperty("userUniquenessId 为 用户唯一身份标志")
  private String userUniquenessId;

  @ApiModelProperty("创建时间  请勿传递")
  private Date createTime;
}
