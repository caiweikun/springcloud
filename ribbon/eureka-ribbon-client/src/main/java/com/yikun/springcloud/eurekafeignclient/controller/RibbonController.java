package com.yikun.springcloud.eurekafeignclient.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 该方法用restTemplate调用eureka-client的API接口  Uri 上不需要使用硬编码（比如IP），只需要写服务名eureka-client即可
     * 程序会根据服务名称 eureka-client到Eureka-server注册中心去自动获取IP和端口信息。
     * @param name
     * @return
     */
    @GetMapping("/hello")
    @HystrixCommand(fallbackMethod = "helloError")
    public String hello(@RequestParam(required = false, defaultValue = "jenny") String name){
        return restTemplate.getForObject("http://eureka-client/hello?name="+name, String.class);
    }

    /**
     * 如果调用失败，则执行 fallbackMethod 的逻辑
     * @param name
     * @return
     */
    private String helloError(String name){
        return "hello ,"+name+" ,sorry,error!";
    }

    @Autowired
    private LoadBalancerClient loadBalancer;

    /**
     * 通过LoadBalancerClient 去选择一个eureka-client 的服务实例的信息， 并将该信息返回
     */
    @GetMapping("/testRibbon")
    public String  testRibbon() {
        ServiceInstance instance = loadBalancer.choose("eureka-client");
        return instance.getHost()+":"+instance.getPort();
    }
}
