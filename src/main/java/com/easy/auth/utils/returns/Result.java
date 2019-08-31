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
 * <p>
 * <p>
 * <p>
 * `@JsonInclude(JsonInclude.Include.NON_NULL)  //为 不输出  为null 的key
 */
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = 8094214056827400324L;
    private Boolean success;
    private Integer code;

    private String msg;

    private Object data;

    public Result() {
    }

    public Result(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Result(String msg, Boolean success) {
        this.success = success;
        this.msg = msg;
    }

    public Result(String msg, Boolean success, Object dataObj) {
        this.success = success;
        this.msg = msg;
        this.data = dataObj;
    }

    public Result(String msg) {
        this.success = false;
        this.msg = msg;
        this.code = 200;
    }

    public static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        result.setData(true);
        result.setCode(200);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(200);
        result.setData(data);
        return result;
    }


    public static Result success(String msg, Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setSuccess(true);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }


    public static Result failure(String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(-200);
        result.setMsg(msg);
        return result;
    }

    public static Result failure(String msg, Object data) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(-200);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static Result failure() {
        Result result = new Result();
        result.setCode(-200);
        result.setSuccess(false);
        result.setMsg("操作失败!");
        return result;
    }

    public static Result failure(Object object) {
        Result result = new Result();
        result.setCode(-200);
        result.setSuccess(false);
        result.setData(object);
        return result;
    }


    public static Result fail() {
        Result result = new Result();
        result.setCode(-200);
        result.setSuccess(false);
        result.setData(null);
        result.setMsg("操作失败!");
        return result;
    }

    public static Result fail(String msg) {
        Result result = new Result();
        result.setCode(-200);
        result.setSuccess(false);
        result.setData(null);
        result.setMsg(msg);
        return result;
    }

    public static Result fail(String msg, Object o) {
        Result result = new Result();
        result.setCode(-200);
        result.setSuccess(false);
        result.setData(o);
        result.setMsg(msg);
        return result;
    }

    public Boolean judgeSuccess() {
        return this.getSuccess();
    }

    public void setResultCode(ResultCode resultCode) {

        if (resultCode.code() == 1) {
            setCode(200);
            setSuccess(true);
        } else {
            setCode(-200);
            setSuccess(false);
        }
        setMsg(resultCode.message());
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
