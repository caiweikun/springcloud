package com.yikun.springcloud.eurekamonitorclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * 在使用 Hystrix Dashboard 组件监控服务的熔断情况的时候，每个服务都有一个 Hystrix Dashboard 主页，当服务数量很多时候，监控非常不方便
 * Turbine 用于聚合多个Hystrix Dashboard  将多个 Hystrix Dashboard 的数据放在一个页面展示，集中监控
 *
 * 需要引入起步依赖 hystrix-dashboard  turbine  actuator
 *
 * 数据流 http://localhost:port/turbine.stream
 * 仪表盘界面 http://localhost:port/hystrix
 */
@EnableTurbine
@EnableEurekaClient
@EnableHystrixDashboard
@SpringBootApplication
public class EurekaMonitorClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaMonitorClientApplication.class, args);
    }

}
