package com.yikun.springcloud.actuator.service.impl;

import com.yikun.springcloud.actuator.entity.User;
import com.yikun.springcloud.actuator.service.RabbitMqService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**** imports ****/
@Service
public class RabbitMqServiceImpl 
        // 实现ConfirmCallback接口，这样可以回调
        implements ConfirmCallback, RabbitMqService {

    @Value("${rabbitmq.queue.msg}")
    private String msgRouting = null;
    
    @Value("${rabbitmq.queue.user}")
    private String userRouting = null;
    
    // 注入由Spring Boot自动配置的RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate = null;

    // 发送消息
    @Override
    public void sendMsg(String msg) {
        System.out.println("发送消息: 【" + msg + "】");
        // 设置回调
        rabbitTemplate.setConfirmCallback(this);
        // 发送消息，通过msgRouting确定队列
        rabbitTemplate.convertAndSend(msgRouting, msg);
    }

    // 发送用户
    @Override
    public void sendUser(User user) {
        System.out.println("发送用户消息: 【" + user + "】");
        // 设置回调
        rabbitTemplate.setConfirmCallback(this);
        // 发送消息，通过userRouting确定队列
        // 待发送的user对象必须实现可序列化接口
        rabbitTemplate.convertAndSend(userRouting, user);
    }

    // 回调确认方法
    @Override
    public void confirm(CorrelationData correlationData,
                        boolean ack, String cause) {
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + cause);
        }
    }

}