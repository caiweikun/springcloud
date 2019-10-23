package com.yikun.springcloud.actuator.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Value("${my.name}")
    private String name;
    @Value("${my.age}")
    private String age;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }

    @RequestMapping("/my")
    @ResponseBody
    public String my(){
        return "name:"+name+",age:"+age;
    }
}
