package com.easy.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 消息提示相关
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 * <p>
 * Swagger2配置类 在与spring boot集成时，放在与Application.java同级的目录下。 通过@Configuration注解，让Spring来加载该类配置。
 * * 再通过@EnableSwagger2注解来启用Swagger2。
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Configuration
@EnableSwagger2
@PropertySource({"classpath:application.properties"})
public class Swagger2 {
    // 定义分隔符
    private static final String splitor = ";";
    @Value("${server.port}")
    private String serverPort;

    /**
     * 创建API应用 apiInfo() 增加API相关信息 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(io.swagger.annotations.Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中） 访问地址：http://项目实际地址/swagger-ui.html
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("自动构建前后端对接接口文档")
                .description("存疑请联系后台开发连晋 没有特别说明为url传参的接口 默认都使用 使用RequestBody接收前端的参数")
                .termsOfServiceUrl("http://127.0.0.1:" + serverPort)
                .contact("连晋")
                .version("1.0")
                .build();
    }
}
