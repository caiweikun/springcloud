package com.yikun.springcloud.actuator.service;

import com.yikun.springcloud.actuator.entity.User;

public interface RabbitMqService {
	// 发送字符消息
    public void sendMsg(String msg);
    
    // 发送用户消息
    public void sendUser(User user);
}
