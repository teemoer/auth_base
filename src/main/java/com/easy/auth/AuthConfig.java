package com.easy.auth;

import com.easy.auth.common.handler.RoleValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 权限校验 的添加 和 部分静态资源 放行
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Configuration
@Order(2)
public class AuthConfig extends WebMvcConfigurationSupport {
  @Autowired RoleValid roleValid;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
            .addInterceptor(roleValid)
            .addPathPatterns("/**")
            .excludePathPatterns("/common/upload")
            .excludePathPatterns("/ueditor/config")
            .excludePathPatterns("/**.js")
            .excludePathPatterns("/**.css")
            .excludePathPatterns("/webjars/**")
            .excludePathPatterns("/swagger-ui.html");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 文件磁盘图片url 映射
    // 配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
//    registry.addResourceHandler("/file/**").addResourceLocations(SysConstans.FILE_FOUND_PATH);

    registry
        .addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");

    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    registry
        .addResourceHandler("/webjars/springfox-swagger-ui/lang/en.js")
        .addResourceLocations(
            "classpath:/META-INF/resources/webjars/springfox-swagger-ui/lang/zh.js");
    //
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // 设置允许跨域的路径
    registry
        .addMapping("/**")
        // 设置允许跨域请求的域名
        .allowedOrigins("*")
        // 是否允许证书 不再默认开启
        .allowCredentials(true)
        // 设置允许的方法
        .allowedMethods("*")
        // 跨域允许时间
        .maxAge(3600);
  }
}
