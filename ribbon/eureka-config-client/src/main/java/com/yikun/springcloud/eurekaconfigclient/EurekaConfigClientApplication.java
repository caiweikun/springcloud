package com.yikun.springcloud.eurekaconfigclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EurekaConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaConfigClientApplication.class, args);
    }

}
