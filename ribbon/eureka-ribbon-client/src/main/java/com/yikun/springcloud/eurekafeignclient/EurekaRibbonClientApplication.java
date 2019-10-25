package com.yikun.springcloud.eurekafeignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 介绍： 负载均衡器的核心类为LoadBalancerClient, LoadBalancerClient 可以获取负载均衡的服务提供者的实例信息。
 * 在RibbonController类中写一个接口"/testRibbon"， 通过LoadBalancerClient 去选择一个eureka-client 的服务实例的信息， 并将该信息返回。
 * 本示例只演示负载均衡器LoadBalancerClient 是从Eureka Client 获取服务注册列表信息的，并将服务注册列表信息缓存了一份。
 * 在LoadBalancerClient 调用choose（）方法时，根据负载均衡策略选择一个服务实例的信息，从而进行了负载均衡。
 * 注意： eureka client从eureka server获取注册列表信息，LoadBalancerClient从本地的eureka client获取注册列表。
 * LoadBalancerClient 也可以不从Eureka Client 获取注册列表信息，这时需要自己维护一份服务注册列表信息。
 *
 * Hystrix：
 * 在分布式系统中，服务与服务之间的依赖错综复杂， 一种不可避免的情况就是某些服务会出现故障，导致依赖于它们的其他服务出现远程调度的线程阻塞。
 * Hystrix 是Netflix 公司开源的一个项目，它提供了熔断器功能，能够阻止分布式系统中出现联动故障。Hystrix 是通过隔离服务的访问点阻止联动故障的，
 * 并提供了故障的解决方案，从而提高了整个分布式系统的弹性。
 * 工作机制：
 * 首先，当服务的某个API 接口的失败次数在一定时间内小于设定的阀值时，熔断器处于关闭状态，该API 接口正常提供服务。当该API 接口处理请求的失败次数大于设定的阀值时，
 * Hystrix 判定该API 接口出现了故障，打开熔断器，这时请求该API 接口会执行快速失败的逻辑（即fallback 回退的逻辑），不执行业务逻辑，请求的线程不会处于阻塞状态。
 * 处于打开状态的熔断器， 一段时间后会处于半打开状态，并将一定数量的请求执行正常逻辑。剩余的请求会执行快速失败，若执行正常逻辑的请求失败了，则熔断器继续打开；
 * 若成功了，则将熔断器关闭。这样熔断器就具有了自我修复的能力。
 *
 * 在 RestTemplate 和 Ribbon 上使用熔断器
 * 1，引入 spring-cloud-starter-netflix-hystrix 启动器
 * 2，开启 @EnableHystrix
 * 3，在调用的服务的业务方法上 加注解@HystrixCommand 并 重写fallbackMethod 调用错误逻辑
 *
 * 使用 Hystrix Dashboard 监控熔断器的状态
 * 引入起步依赖 spring-cloud-starter-netflix-hystrix-dashboard
 * 必须引入 spring-boot-starter-actuator 的起步依赖，并配置 management.endpoints.web.exposure.include=* 暴露所有端点，至少暴露 hystrix.stream
 * 开启 @EnableHystrixDashboard
 * 访问 http://localhost:port/actuator/hystrix.stream 浏览器上就会显示熔断器的数据指标
 * 或者 Hystrix仪表盘配置界面 http://localhost:port/hystrix
 * 更多关于指标的参数说明 请查询 https://github.com/Netflix/Hystrix/wiki/Dashboard
 */
@EnableEurekaClient
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication
public class EurekaRibbonClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaRibbonClientApplication.class, args);
    }

}
