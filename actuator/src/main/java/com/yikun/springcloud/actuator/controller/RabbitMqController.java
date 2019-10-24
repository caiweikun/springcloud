package com.yikun.springcloud.actuator.controller;

import com.yikun.springcloud.actuator.entity.User;
import com.yikun.springcloud.actuator.service.RabbitMqService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMqController {
    // 注入Spring Boot自定生成的对象
    @Autowired
    private RabbitMqService rabbitMqService = null;

    @ApiOperation(value="rabbit发送字符串消息", notes="rabbit发送字符串消息")
    @GetMapping("/msg") // 字符串
    public Map<String, Object> msg(String message) {
        rabbitMqService.sendMsg(message);
        return resultMap("message", message);
    }

    @ApiOperation(value="rabbit发送User对象", notes="rabbit发送User对象")
    @GetMapping("/user") // 用户
    public Map<String, Object> user(User user) {
        rabbitMqService.sendUser(user);
        return resultMap("user", user);
    }

    // 结果Map
    private Map<String, Object> resultMap(String key, Object obj) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put(key, obj);
        return result;
    }
}
