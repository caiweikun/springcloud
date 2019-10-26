package com.ykun.springcloud.eurekaconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 一般来说 Config Server 需要构建高可用到集群，并把他们注册到Eureka Server 服务治理中心
 *
 * Config Server 可以从本地仓库读取配置文件，也可以从远处Git 仓库读取。本地仓库是指将所有的配置文件统一写在Config Server 工程目录下。
 * 在ConfigServerApplication类上新增@EnableConfigServer注解，开启config server功能。
 *
 */
@EnableEurekaClient
@EnableConfigServer
@SpringBootApplication
public class EurekaConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaConfigServerApplication.class, args);
    }

}
