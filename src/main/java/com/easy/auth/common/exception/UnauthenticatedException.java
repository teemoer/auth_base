package com.easy.auth.common.exception;

/**
 * 未授权异常
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public class UnauthenticatedException extends RuntimeException {
    public UnauthenticatedException(String s) {
        super(s);
    }

    public UnauthenticatedException() {
        super();
    }
}
