package com.easy.auth.bean;

import com.easy.auth.common.AdminLoginFormDbDto;
import com.easy.auth.common.enums.common.EnableStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 普通用户
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
@ApiModel(" 普通用户 json")
public class User extends AdminLoginFormDbDto {
    @ApiModelProperty(value = "", notes = "")
    private Integer id;
    @ApiModelProperty(value = "登录用户名", notes = "")
    private String userName;
    @ApiModelProperty(value = "", notes = "")
    /*
     * json序列化向前端输出内容的时候
     *
     * 不输出数据库中存储的密码
     */
    private String password;
    @ApiModelProperty(value = "是否启用", notes = "")
    private EnableStatusEnum enableStatus;
    @ApiModelProperty(value = "创建时间", notes = "")
    private java.util.Date createDate;
    @ApiModelProperty(value = "用户唯一标识  为uuid", notes = "")
    private String uniquenessId;

    @Override
    public void setId(Integer id) {
        super.setId(id);
        this.id = id;
    }

    @Override
    public void setUserName(String userName) {
        super.setUserName(userName);
        this.userName = userName;
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.password = password;
    }

    @Override
    public void setEnableStatus(EnableStatusEnum enableStatus) {
        super.setEnableStatus(enableStatus);
        this.enableStatus = enableStatus;
    }


    @Override
    public void setUniquenessId(String uniquenessId) {
        super.setUniquenessId(uniquenessId);
        this.uniquenessId = uniquenessId;
    }
}
