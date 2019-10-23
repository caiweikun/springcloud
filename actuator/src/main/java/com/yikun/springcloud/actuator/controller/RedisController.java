package com.yikun.springcloud.actuator.controller;

import com.yikun.springcloud.actuator.dao.RedisDao;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/redis")
@RestController
public class RedisController {

    @Autowired
    private RedisDao redisDao;

    @ApiOperation(value="redis新增键值", notes="redis新增键值")
    @GetMapping(value={"set"})
    public String setValue(@RequestParam(value = "key") String key
            , @RequestParam(value = "value") String value) {
        redisDao.setKey(key, value);
        return key;
    }

    @ApiOperation(value="redis获取键值", notes="redis获取键值")
    @GetMapping(value={"get"})
    public String getValue(@RequestParam(value = "key") String key) {
        return redisDao.getValue(key);
    }

}


