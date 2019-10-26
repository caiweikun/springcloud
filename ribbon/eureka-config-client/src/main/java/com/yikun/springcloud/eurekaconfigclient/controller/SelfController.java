package com.yikun.springcloud.eurekaconfigclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// spring cloud bus 更新配置
@RefreshScope
public class SelfController {

    @Value("${self.name}")
    private String name;
    @Value("${self.age}")
    private Integer age;

    @Value("${self.code}")
    private String code;

    @GetMapping("/self")
    public String selfInfo(){
        return "name:"+name+",age:"+age;
    }

    @GetMapping("/self/code")
    public String selfInfoCode(){
        return "code:"+code;
    }
}
