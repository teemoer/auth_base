package com.easy.auth.utils.servlet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * http request 工具类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Component
public class RequestUtils {
    private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    /**
     * 获取客户端请求参数中所有的信息
     *
     * @param request
     * @return
     */
    public static String getAllRequestParam(HttpServletRequest request) {

        String requestUrl = request.getRequestURI();
        /*
         * 排除  百度UE编辑器  和  swagger2
         */
        if ("/common/upload,/ueditor/config,/swagger-ui.html,/file/**,/**.js,/**.css,/webjars/**"
                .contains(requestUrl)) {
            return "";
        }
        Map<String, String> res = new HashMap<>(16);
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 如果字段的值为空，判断若值为空，则删除这个字段>
                if (StringUtils.isNotBlank(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        String endStr = res.toString();
        if (res.size() == 0) {
            try {
                BufferedReader br = request.getReader();

                String str;
                StringBuilder wholeStr = new StringBuilder();
                while ((str = br.readLine()) != null) {
                    wholeStr.append(str);
                }

                endStr = wholeStr.toString();
            } catch (Exception e1) {
                logger.error("自动提取参数失败,", e1);
            }
        }
        return endStr;
    }
}
