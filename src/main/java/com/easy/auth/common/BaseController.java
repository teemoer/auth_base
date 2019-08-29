package com.easy.auth.common;

import com.easy.auth.utils.user.UserInfoUtils;

/** @author 连晋 */
public abstract class BaseController {

  /**
   * 获取当前登录 用户信息
   *
   * @return
   */
  public AdminLoginFormDbDto getNowLoginUser() {
    return UserInfoUtils.getUser();
  }
}
