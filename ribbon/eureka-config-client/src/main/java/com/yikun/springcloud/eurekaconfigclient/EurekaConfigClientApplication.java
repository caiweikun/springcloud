package com.yikun.springcloud.eurekaconfigclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * netstat -AaLlnW
 * 使用 Spring Cloud Bus 刷新配置
 * 为了测试 消息总线的刷新功能，开启了两个 config-client 实例 配置 分别为 dev 和 test
 * 在需要刷新的类上 @RefreshScope
 * 请求刷新的页面由原来1.5.x的localhost:port/bus/refresh变成：http://localhost:port/actuator/bus-refresh。
 * 请求集群中任意一个 使用Postman 发送 POST请求 http://localhost:8762/actuator/bus-refresh 则集群中所有的配置都会更新
 * 如 /actuator/bus-refresh?destination=eureka-client:** 则刷新服务名为eureka-client 的所有服务实例
 *
 *
 */
@EnableEurekaClient
@SpringBootApplication
public class EurekaConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaConfigClientApplication.class, args);
    }

}
