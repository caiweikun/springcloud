package com.yikun.springcloud.eurekafeignclient.client.hystrix;

import com.yikun.springcloud.eurekafeignclient.client.EurekaClientFeign;
import org.springframework.stereotype.Component;

/**
 * spring-cloud-starter-openfeign 的启动器 自带了 Hystrix 和 ribbon
 * 如果请求失败，则进入熔断器的方法，快速失败
 */
@Component
public class EurekaClientHystrix implements EurekaClientFeign {

    @Override
    public String sayHiFromClientEureka(String name) {
        return "hello, "+name+" ,请求出错，进入Hystrix了";
    }
}
