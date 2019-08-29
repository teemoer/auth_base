package com.easy.auth.enums.base;

/**
 * BaseEnum
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public interface BaseEnum<E extends Enum<?>, T> {
  public T getValue();

  public String getDisplayName();
}
