package com.yikun.springcloud.actuator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Configuration 注解， 表明是一个配置类，加上@EnableSwagger2 开启Swagger2 的功能
 *
 * 写生成文档的注解
 * Swagger2 通过注解来生成API 接口文档，文档信息包括接口名、请求方法、参数、返回信息等。通常’悄况下用于生成在线API 文档，以下的注解能够满足基本需求，注解及其描述如下。
 * @Api ： 修饰整个类，用于描述Controller 类。
 * @ApiOperation ：描述类的方法，或者说一个接口。
 * @ApiParam ： 单个参数描述。
 * @ApiModel ：用对象来接收参数。
 * @ApiProperty ：用对象接收参数时，描述对象的一个字段。
 * @ApiResponse: HTTP 响应的一个描述。
 * @ApiResponses: HTTP 响应的整体描述。
 * @Apilgnore ：使用该注解，表示Swagger2 忽略这个API 。
 * @ApiError ： 发生错误返回的信息。
 * @ApiParamlmplicit ： 一个请求参数。
 * @ApiParamsimplicit ： 多个请求参数。
 *
 * 启动服务，浏览器访问： http://localhost:8080/swagger-ui.html 查看在线API
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    /**
     * Swagger2 中需要注入一个Docket 的Bean ， 该 Bean 包含了apiInfo ，即基本API 文档的描述信息，以及包扫描的基本包名等信息
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yikun.springcloud.actuator"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")
                .description("简单优雅的restfun风格")
                .termsOfServiceUrl("http://yikun.info")
                .version("1.0")
                .build();
    }
}
