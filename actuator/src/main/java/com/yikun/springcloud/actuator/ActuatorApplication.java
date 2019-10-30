package com.yikun.springcloud.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//定义JPA接口扫描包路径
@EnableJpaRepositories(basePackages = "com.yikun.springcloud.actuator.dao")
//定义实体Bean扫描包路径
@EntityScan(basePackages = "com.yikun.springcloud.actuator.entity")

@SpringBootApplication
public class ActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplication.class, args);
    }

}
