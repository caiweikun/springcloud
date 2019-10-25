package com.yikun.springcloud.eurekafeignclient.controller;

import com.yikun.springcloud.eurekafeignclient.client.EurekaClientFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private EurekaClientFeign eurekaClientFeign;

    /**
     * 该方法用restTemplate调用eureka-client的API接口  Uri 上不需要使用硬编码（比如IP），只需要写服务名eureka-client即可
     * 程序会根据服务名称 eureka-client到Eureka-server注册中心去自动获取IP和端口信息。
     *
     * @param name
     * @return
     */
    @GetMapping("/hello")
    public String hello(@RequestParam(required = false, defaultValue = "jenny") String name) {
        return eurekaClientFeign.sayHiFromClientEureka(name);
    }

}
