package com.yikun.springcloud.eurekaribbonclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 介绍： 负载均衡器的核心类为LoadBalancerClient, LoadBalancerClient 可以获取负载均衡的服务提供者的实例信息。
 * 在RibbonController类中写一个接口"/testRibbon"， 通过LoadBalancerClient 去选择一个eureka-client 的服务实例的信息， 并将该信息返回。
 * 本示例只演示负载均衡器LoadBalancerClient 是从Eureka Client 获取服务注册列表信息的，并将服务注册列表信息缓存了一份。
 * 在LoadBalancerClient 调用choose（）方法时，根据负载均衡策略选择一个服务实例的信息，从而进行了负载均衡。
 * 注意： eureka client从eureka server获取注册列表信息，LoadBalancerClient从本地的eureka client获取注册列表。
 * LoadBalancerClient 也可以不从Eureka Client 获取注册列表信息，这时需要自己维护一份服务注册列表信息。
 */
@EnableEurekaClient
@SpringBootApplication
public class EurekaRibbonClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaRibbonClientApplication.class, args);
    }

}
