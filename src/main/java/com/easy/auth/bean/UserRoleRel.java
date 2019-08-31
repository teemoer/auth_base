package com.easy.auth.bean;

import com.easy.auth.enums.common.EnableStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户与角色关系 实体类
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
@ApiModel(" 用户与角色关系 json")
public class UserRoleRel {
    @ApiModelProperty(value = "", notes = "")
    private Integer id;
    @ApiModelProperty(value = "对应 role_info 主键id", notes = "")
    private Integer roleId;
    @ApiModelProperty(value = "所有用户表唯一标识符 uniquenessId 可能来自sysUser表也可能来自user表", notes = "")
    private String uniquenessId;
    @ApiModelProperty(value = "把角色与用户关系的操作人员的用户名", notes = "")
    private String createUserName;
    @ApiModelProperty(value = "是否启用枚举标识符", notes = "")
    private EnableStatusEnum enableStatus;
    @ApiModelProperty(value = "用户角色关系记录创建时间", notes = "")
    private java.util.Date createDate;
}
