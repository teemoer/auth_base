package com.easy.auth.common.exception;

/**
 * 未知帐号异常
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public class UnknownAccountException extends RuntimeException {
    public UnknownAccountException() {
    }

    public UnknownAccountException(String message) {
        super(message);
    }
}
