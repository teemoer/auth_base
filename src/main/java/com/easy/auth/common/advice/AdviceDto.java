package com.easy.auth.common.advice;

import com.easy.auth.utils.servlet.RequestUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 类功能描述
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA. @Data: 11:44
 */
@Data
public class AdviceDto {
    private static Logger logger = LoggerFactory.getLogger(AdviceDto.class);
    private String requestUrl;
    private String allRequestParam;

    public AdviceDto(HttpServletRequest httpServletRequest) {
        setRequestUrl(httpServletRequest.getRequestURI());
        setAllRequestParam(RequestUtils.getAllRequestParam(httpServletRequest));
    }
}
