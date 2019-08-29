package com.easy.auth.utils.returns;

import lombok.Data;

import java.io.Serializable;

/**
 * API接口返回结果
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
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {

  private static final long serialVersionUID = 8094214056827400324L;
  private Boolean code;

  private String msg;

  private Object data;

  public Result() {}

  public Result(Boolean code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public Result(String msg, Boolean code) {
    this.code = code;
    this.msg = msg;
  }

  public Result(String msg, Boolean i, Object dataObj) {
    this.code = i;
    this.msg = msg;
    this.data = dataObj;
  }

  public Result(String msg) {
    this.code = false;
    this.msg = msg;
  }

  public static Result success() {
    Result result = new Result();
    result.setCode(true);
    result.setData(true);
    return result;
  }

  public static Result success(Object data) {
    Result result = new Result();
    result.setCode(true);
    result.setData(data);
    return result;
  }





    public static Result success(String msg, Object data) {
        Result result = new Result();
        result.setCode(true);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }



  public static Result failure(String msg) {
    Result result = new Result();
    result.setCode(false);
    result.setMsg(msg);
    return result;
  }
  public static Result failure(String msg,Object  data) {
    Result result = new Result();
    result.setCode(false);
    result.setData(data);
    result.setMsg(msg);
    return result;
  }
  public static Result failure() {
    Result result = new Result();
    result.setCode(false);
    result.setMsg("操作失败!");
    return result;
  }

  public static Result failure(Object object) {
    Result result = new Result();
    result.setCode(false);
    result.setData(object);
    return result;
  }



  public static Result fail() {
    Result result = new Result();
    result.setCode(false);
    result.setData(null);
    result.setMsg("操作失败!");
    return result;
  }

  public static Result fail(String msg) {
    Result result = new Result();
    result.setCode(false);
    result.setData(null);
    result.setMsg(msg);
    return result;
  }
  public static Result fail(String msg,Object o) {
    Result result = new Result();
    result.setCode(false);
    result.setData(o);
    result.setMsg(msg);
    return result;
  }

    public Boolean judgeSuccess() {
        return this.code;
    }

  public void setResultCode(ResultCode resultCode) {
    
    if (resultCode.code()==1){
      setCode(true);
    }else {
      setCode(false);
    }
    setMsg(resultCode.message());
  }
}
