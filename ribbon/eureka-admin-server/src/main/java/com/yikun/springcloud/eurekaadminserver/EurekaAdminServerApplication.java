package com.yikun.springcloud.eurekaadminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 使用 Spring Boot Admin 监控 Spring Cloud 微服务
 * 添加主要依赖 spring-boot-admin-starter-server
 * 开启 @EnableAdminServer @EnableEurekaClient
 * 这样 admin工程就会到 服务注册中心更新数据，其他客户端不用做任何的配置和依赖
 *
 * 官方文档 https://github.com/codecentric/spring-boot-admin
 *
 * 在 Spring Boot Admin 中集成 Turbine , 在之前我们开启了 eureka-monitor-client，使用 turbine 集成了 hystrix-dashboard
 * hystrix-dashboard 是一个监控熔断器状况的组件，而turbine 是一个可以聚合多个hystrix-dashboard 的组件
 *
 * 通过查看官方文档 2.x 之后就没有对 Turbine 做支持了，在此我们就先不去讨论了，读者可以在参考 eureka-monitor-client 集成 Turbine
 *
 * 在Spring Boot Admin 中添加安全登录界面
 * 添加依赖 spring-boot-starter-security
 *
 *
 */
@EnableAdminServer
@EnableEurekaClient
@SpringBootApplication
public class EurekaAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaAdminServerApplication.class, args);
    }

}
