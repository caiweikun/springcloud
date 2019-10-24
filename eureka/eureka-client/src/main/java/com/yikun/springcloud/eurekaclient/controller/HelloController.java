package com.yikun.springcloud.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${server.port}")
    String port;

    @GetMapping("/hello")
    public String home(@RequestParam(required = false) String name) {
        return "hello "+name+",i am from port:" +port+", hi";
    }

}
