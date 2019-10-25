package com.yikun.springcloud.eurekafeignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 * 声明式接口调用Feign
 * Feign流程总结：
 * 总的来说， Feign 的源码实现过程如下：
 * (1) 首先通过@EnableFeignClients 注解开启Feign Client 的功能。只有这个注解存在，才会在程序启动时开启对＠FeignClient 注解的包扫描。
 * (2）根据Feign 的规则实现接口，井在接口上面加上＠FeignClient 注解。
 * (3）程序启动后，会进行包扫描，扫描所有的＠ FeignClient 的注解的类，并将这些信息注入IoC 容器中。
 * (4）当接口的方法被调用时， 通过JDK 的代理来生成具体的RequestTemplate 棋根对象。
 * (5）根据RequestTemplate 再生成Http 请求的Request 对象。
 * (6) Request 对象交给Client 去处理， 其中Client 的网络请求框架可以是HttpURLConnection 、HttpClient 和OkHttp 。
 * (7）最后Client 被封装到LoadBalanceClient 类，这个类结合类Ribbon 做到了负载均衡。
 *
 * 在 Feign 上使用熔断器 Hystrix
 * 在Feign 中不需要引入任何的依赖，因为在Feign的起步依赖中 包含了 Hystrix 和 Ribbon 的相关依赖
 * 1，配置 feign.hystrix.enabled=true
 * 2, 实现Feign接口，重写调用出错时的业务逻辑
 * 3, @FeignClient() 指定 fallback
 *
 * 在 Feign 中使用 Hystrix Dashboard
 * 需要 pom 中加入 Actuator,Hystrix,Hystrix Dashboard  的起步依赖 ，Feign中虽然自带了 Hystrix 但不是起步依赖start
 * 配置 @EnableHystrix  @EnableHystrixDashboard
 * 访问 http://localhost:port/actuator/hystrix.stream 浏览器上就会显示熔断器的数据指标
 * 或者 Hystrix仪表盘配置界面 http://localhost:port/hystrix
 * 更多关于指标的参数说明 请查询 https://github.com/Netflix/Hystrix/wiki/Dashboard
 */
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication
public class EurekaFeignClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaFeignClientApplication.class, args);
    }

}
