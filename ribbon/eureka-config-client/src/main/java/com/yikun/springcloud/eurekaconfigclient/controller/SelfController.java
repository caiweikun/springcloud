package com.yikun.springcloud.eurekaconfigclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SelfController {

    @Value("${self.name}")
    private String name;
    @Value("${self.age}")
    private Integer age;

    @GetMapping("/self")
    public String selfInfo(){
        return "name:"+name+",age:"+age;
    }
}
