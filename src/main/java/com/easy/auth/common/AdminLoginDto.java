package com.easy.auth.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 后台|手机 用户登录dto josn
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
@ApiModel("后台  用户登录dto josn")
public class AdminLoginDto {
  @NotBlank(message = "登录请求必须传递 userName")
  @ApiModelProperty(value = "登录用户名", required = true)
  private String userName;

  @NotBlank(message = "登录请求必须传递 password")
  @ApiModelProperty(value = "加密以后的密码", required = true)
  private String password;
}
