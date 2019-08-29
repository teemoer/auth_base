package com.easy.auth;

import com.easy.auth.common.advice.AdviceDto;
import com.easy.auth.infrastructure.config.redis.utils.RedisUtil;
import com.easy.auth.utils.returns.Result;
import com.easy.auth.utils.returns.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.UUID;

/**
 * 全局异常拦截
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Slf4j
@ControllerAdvice
@ResponseBody
@CrossOrigin
public class ExceptionAdvice {

  private static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

  /** 400 - Bad Request */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({
          HttpMessageNotReadableException.class,
          MissingServletRequestParameterException.class,
          BindException.class,
          ServletRequestBindingException.class,
          MethodArgumentNotValidException.class,
          ConstraintViolationException.class
  })
  public Result handleHttpMessageNotReadableException(
          HttpServletRequest httpServletRequest, Exception e) {
    AdviceDto requestInfo = getRequestInfo(httpServletRequest);

    Result result = new Result();
    result.setData("参数解析失败,错误信息为:" + e.getMessage() + " \n错误编号为:" + generateErorCode());
    logger.error(
            "\n解析参数400报错,"
                    + ResultCode.BAD_REQUEST.message()
                    + " \nurl:"
                    + requestInfo.getRequestUrl()
                    + "\n请求参数为:\n"
                    + requestInfo.getAllRequestParam()
                    + "\n"
                    + result.getData().toString(),
            e);
    if (e instanceof BindException) {

      result.setMsg(ResultCode.BAD_REQUEST.message());
      return result;
    }

    result.setMsg(ResultCode.BAD_REQUEST.message());

    return result;
  }

  /** 405 - Method Not Allowed */
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public Result handleHttpRequestMethodNotSupportedException(
          HttpRequestMethodNotSupportedException e, HttpServletRequest httpServletRequest) {
    AdviceDto requestInfo = getRequestInfo(httpServletRequest);
    Result result = new Result();
    result.setResultCode(ResultCode.METHOD_NOT_ALLOWED);
    result.setData("错误编号为:" + generateErorCode());
    logger.error(
            "\n请求405跨域错误,"
                    + ResultCode.METHOD_NOT_ALLOWED.message()
                    + " \nurl:"
                    + requestInfo.getRequestUrl()
                    + "\n请求参数为:\n"
                    + requestInfo.getAllRequestParam()
                    + "\n"
                    + result.getData().toString(),
            e);
    return result;
  }

  @ExceptionHandler(UnknownAccountException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Result handleUnknownAccountException(
          UnknownAccountException e, HttpServletRequest httpServletRequest) {
    AdviceDto requestInfo = getRequestInfo(httpServletRequest);
    Result result = new Result();
    result.setResultCode(ResultCode.INVALID_TOKEN);
    result.setData("错误编号为:" + generateErorCode());
    logger.error(
            "\n请求授权错误,"
                    + ResultCode.INVALID_TOKEN.message()
                    + " \nurl:"
                    + requestInfo.getRequestUrl()
                    + "\n请求参数为:\n"
                    + requestInfo.getAllRequestParam()
                    + "\n"
                    + result.getData().toString(),
            e);

    return result;
  }

  @ExceptionHandler(AuthorizationException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Result handleHttpRequestMethodNotSupportedException(
          AuthorizationException e, HttpServletRequest httpServletRequest) {
    AdviceDto requestInfo = getRequestInfo(httpServletRequest);
    Result result = new Result();
    result.setResultCode(ResultCode.INVALID_TOKEN);
    result.setData("错误编号为:" + generateErorCode());
    logger.error(
            "\n请求授权错误,"
                    + ResultCode.INVALID_TOKEN.message()
                    + " \nurl:"
                    + requestInfo.getRequestUrl()
                    + "\n请求参数为:\n"
                    + requestInfo.getAllRequestParam()
                    + "\n"
                    + result.getData().toString(),
            e);
    return result;
  }

  /**
   * shiro权限异常处理
   *
   * @return
   */
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.OK)
  public Result unauthorizedException(
          UnauthorizedException e, HttpServletRequest httpServletRequest) {
    AdviceDto requestInfo = getRequestInfo(httpServletRequest);
    Result failure = Result.failure(ResultCode.UNAUTHO_ERROR);
    failure.setData("错误编号为:" + generateErorCode());
    logger.error(
            "\n请求授权错误,"
                    + ResultCode.UNAUTHO_ERROR.message()
                    + " \nurl:"
                    + requestInfo.getRequestUrl()
                    + "\n请求参数为:\n"
                    + requestInfo.getAllRequestParam()
                    + "\n"
                    + failure.getData().toString(),
            e);
    return failure;
  }

  @ExceptionHandler(UnauthenticatedException.class)
  @ResponseStatus(HttpStatus.OK)
  public Result unauthenticatedException(
          UnauthenticatedException e, HttpServletRequest httpServletRequest) {
    AdviceDto requestInfo = getRequestInfo(httpServletRequest);
    RedisUtil.adminOperatingSysUnitmanageModule();
    Result result = new Result();
    result.setData("错误编号为:" + generateErorCode());
    logger.error(
            "\n请求授权错误,"
                    + ResultCode.UNAUTHO_ERROR.message()
                    + " \nurl:"
                    + requestInfo.getRequestUrl()
                    + "\n请求参数为:\n"
                    + requestInfo.getAllRequestParam()
                    + "\n"
                    + result.getData().toString(),
            e);
    result.setResultCode(ResultCode.UNAUTHO_ERROR);
    return result;
  }

  /**
   * 500
   *
   * @param e
   * @return
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public Result handleMethodArgumentTypeMismatchException(
          Exception e, HttpServletRequest httpServletRequest) {
    AdviceDto requestInfo = getRequestInfo(httpServletRequest);
    Result result = new Result();
    result.setResultCode(ResultCode.ARGUMENT_TYPE_MISMATCH);
    result.setData("参数类型转化错误信息为:" + e.getMessage() + " \n 错误编号为:" + generateErorCode());
    logger.error(
            "\n请求服务器错误,参数转化异常 \nurl:"
                    + requestInfo.getRequestUrl()
                    + "\n请求参数为:\n"
                    + requestInfo.getAllRequestParam()
                    + "\n"
                    + result.getData().toString(),
            e);
    return result;
  }

  /**
   * 500
   *
   * @param e
   * @return
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public Result handleException(Exception e, HttpServletRequest httpServletRequest) {
    AdviceDto requestInfo = getRequestInfo(httpServletRequest);
    Result result = new Result();
    result.setResultCode(ResultCode.SYSTEM_ERR);
    result.setData("错误编号为:" + generateErorCode());
    logger.error(
            "\n请求服务器错误,服务器内部错误 \nurl:"
                    + requestInfo.getRequestUrl()
                    + "\n请求参数为:\n"
                    + requestInfo.getAllRequestParam()
                    + "\n"
                    + result.getData().toString(),
            e);
    return result;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  public Result handle404Exception(
          NoHandlerFoundException e, HttpServletRequest httpServletRequest) {
    AdviceDto requestInfo = getRequestInfo(httpServletRequest);
    Result result = new Result();
    result.setCode(false);
    result.setMsg("接口:" + requestInfo.getRequestUrl() + " 不存在！");
    result.setData("错误编号为:" + generateErorCode());
    logger.error(
            "\n请求服务器错误,服务器内部错误 \nurl:"
                    + requestInfo.getRequestUrl()
                    + "\n请求参数为:\n"
                    + requestInfo.getAllRequestParam()
                    + "\n"
                    + result.getData().toString(),
            e);
    return result;
  }

  private AdviceDto getRequestInfo(HttpServletRequest httpServletRequest) {
    return new AdviceDto(httpServletRequest);
  }

  private String generateErorCode() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
