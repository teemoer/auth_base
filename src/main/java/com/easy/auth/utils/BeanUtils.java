package com.easy.auth.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * BeanUtils
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public class BeanUtils {

  public static <T> T transferObjectIgnoreCase(Object obj, Class<?> clz) {
    T result = null;
    try {
      if (obj != null && !obj.equals("")) {
        result = (T) clz.newInstance();
        Map<String, Field> destPropertyMap = new HashMap<>();
        for (Field curField : clz.getDeclaredFields()) {
          destPropertyMap.put(curField.getName().toLowerCase(), curField);
        }
        for (Field curField : obj.getClass().getDeclaredFields()) {
          Field targetField = destPropertyMap.get(curField.getName().toLowerCase());
          if (targetField != null) {
            targetField.setAccessible(true);
            curField.setAccessible(true);
            targetField.set(result, curField.get(obj));
          }
        }
      }
    } catch (Exception e1) {
      return null;
    }
    return result;
  }

  /**
   * 类型不同不可以转换 但是 大小写可以忽略 下划线 _ 被忽略
   *
   * @param source
   * @param target
   * @param <T>
   * @return
   */
  public static <T> T copyProperties(Object source, Object target) {
    Map<String, Field> sourceMap = CacheFieldMap.getFieldMap(source.getClass());
    CacheFieldMap.getFieldMap(target.getClass())
        .values()
        .forEach(
            (it) -> {
              Field field = sourceMap.get(it.getName().toLowerCase().replace("_", ""));
              if (field != null) {
                it.setAccessible(true);
                field.setAccessible(true);
                try {
                  it.set(target, field.get(source));
                } catch (IllegalAccessException e) {
                  e.printStackTrace();
                }
              }
            });
    return (T) target;
  }

  private static class CacheFieldMap {
    private static Map<String, Map<String, Field>> cacheMap = new HashMap<>();

    private static Map<String, Field> getFieldMap(Class clazz) {
      Map<String, Field> result = cacheMap.get(clazz.getName());
      if (result == null) {
        synchronized (CacheFieldMap.class) {
          if (result == null) {
            Map<String, Field> fieldMap = new HashMap<>();
            for (Field field : clazz.getDeclaredFields()) {
              fieldMap.put(field.getName().toLowerCase().replace("_", ""), field);
            }
            cacheMap.put(clazz.getName(), fieldMap);
            result = cacheMap.get(clazz.getName());
          }
        }
      }
      return result;
    }
  }
}
