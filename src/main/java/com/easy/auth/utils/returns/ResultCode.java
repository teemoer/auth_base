package com.easy.auth.utils.returns;


/**
 * 返回代码枚举
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public enum ResultCode {
  OK(1, "成功"),
  INVALID_REQUEST(2, "请求过于频繁,请稍后"),
  FILE_IS_NULL(3, "未发现附件"),

  LOGIN_ERR(4, "用户名或密码错误"),
  PASSWORD_TYPE_ERR(5, "密码格式错误"),
  BACK_DATA_ERR(6, "数据备份失败"),
  REDUCTION_DATA_ERR(7, "数据还原失败"),
  SIGN_ERROR(120, "签名错误"),
  TIME_OUT(130, "访问超时"),
  BAD_REQUEST(400, "参数解析失败"),
  INVALID_TOKEN(401, "无效的授权码"),
  INVALID_CLIENTID(402, "无效的密钥"),
  METHOD_NOT_ALLOWED(405, "不支持当前请求方法"),
  PARAM_ERR(406, "参数错误"),
  USER_NOT_FOUND(407, "操作的用户不存在"),
  AUTH_INFO_ERROR(408, "用户认证信息不存在"),
  DATA_ERROR(409, "数据错误"),

  SYSTEM_ERR(500, "服务器运行异常"),
  DOT_NOT_FOUND(501, "服务器运行异常"),

  NOT_EXIST_USER_OR_ERROR_PWD(10000, "该用户不存在或密码错误"),
  LOGINED_IN(10001, "该用户已登录"),
  UNAUTHO_ERROR(10004, "您没有该权限"),
  BIND_PHONE(10010, "请绑定手机号"),
  UPLOAD_ERROR(20000, "上传文件异常"),
  FILE_TYPE_ERR(20001, "不支持的文件类型"),
  FILE_TOO_BIG(20002, "过大的文件"),

  PHONE_IS_ERROR(20013, "手机号错误！"),
  PHONE_IS_EXISTED(50022, "手机号已被注册！"),

  USER_FROZEN(40000, "该用户已被冻结"),
  ARGUMENT_TYPE_MISMATCH(400, "请求参数转化失败!");

  private Integer code;

  private String message;

  ResultCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer code() {
    return this.code;
  }

  public String message() {
    return this.message;
  }

  public static String getMessage(String name) {
    for (ResultCode item : ResultCode.values()) {
      if (item.name().equals(name)) {
        return item.message;
      }
    }
    return name;
  }

  public static Integer getCode(String name) {
    for (ResultCode item : ResultCode.values()) {
      if (item.name().equals(name)) {
        return item.code;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return this.name();
  }
}
