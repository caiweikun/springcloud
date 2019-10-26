package com.yikun.springcloud.eurekazuulclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul 作为Netflix 组件，可以与Ribbon 、Eureka 和Hystrix 等组件相结合，实现负载均衡、熔断器的功能。在默认情况下， Zuul 和Ribbon 相结合， 实现了负载均衡的功能。
 * 在Zuul 中实现熔断功能需要实现FallbackProvider 的接口,实现该接口有两个方法， 一个是getRouteO方法，用于指定熔断功能应用于哪些路由的服务；
 * 另一个方法fallbackResponse()为进入熔断功能时执行的逻辑。
 *
 * 自定义过滤器只需要继承ZuulFilter ，井实现ZuulFilter 中的抽象方法，包括filterType()和filterOrder()，以及IZuulFilter 的shouldFilter()和run()的两个方法。
 * filterType()即过滤器的类型，有4 种类型，分别是"pre" "post" "routing" 和 "error";
 * filterOrder()是过滤顺序，它为一个Int 类型的值，值越小，越早执行该过滤器;
 * shouldFilter()表示该过滤器是否过滤逻辑，如果为true ，则执行run()方法：如果为false ，则不执行run()方法；
 * run()方法写具体的过滤的逻辑。
 *
 * ZuulFilter使用场景总结：
 * 在实际开发中，可以用此过滤器进行安全验证，日志统一记录等。
 *
 * Zuul 是采用了类似于Spring MVC 的DispatchServlet 来实现的，采用的是异步阻塞模型，所以性能比Ngnix 差。由于Zuul 和其他Netflix 组件可以相互配合、无缝集成，
 * Zuul 很容易就能实现负载均衡、智能路由和熔断器等功能。在大多数情况下， Zuul 都是以集群的形式存在的。由于Zuul的横向扩展能力非常好，所以当负载过高时，可以
 * 通过添加实例来解决性能瓶颈。
 *
 * 一种常见的使用方式是对不同的渠道使用不同的Zuul 来进行路由，例如移动端共用一个Zuul 网关实例， Web 端用另一个Zuul 网关实例，其他的客户端用另外一个Zuul 实例进行路由。
 * 另外一种常见的集群是通过Ngnix 和Zuul 相互结合来做负载均衡。暴露在最外面的是Ngnix 主从双热备进行Keepalive, Ngnix 经过某种路由策略，将请求路由转发到Zuul 集群上，
 * Zuul 最终将请求分发到具体的服务上。
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class EurekaZuulClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaZuulClientApplication.class, args);
    }

}
