package com.easy.auth.utils;

import com.easy.auth.utils.returns.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GetErrors
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public class GetErrors {

  public static Result getErrorMsg(BindingResult bResult) {
    Map<String, Object> map = new HashMap<>(16);
    Result result = new Result();

    if (bResult == null) {
      /*
       * 如果 sprng 注入的 bResult 为 null
       *
       * 说明 被调用的接口 参数检验无错误
       *
       * 则直接返回 成功无错误异常检验
       */
      return Result.success(null);
    }

    List<ObjectError> errors = bResult.getAllErrors();
    String errorMes = "";
    if (errors.size() > 0) {
      for (ObjectError objectError : errors) {
        FieldError fieldError = (FieldError) objectError;
        errorMes = fieldError.getDefaultMessage();
        map.put(fieldError.getField(), errorMes);
      }
      result.setSuccess(false);
      result.setMsg(errorMes);
      result.setData(map);
      return result;
    }
    result.setSuccess(true);
    return result;
  }
}
