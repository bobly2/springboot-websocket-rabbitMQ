package com.example.chat.utils;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitProducer {
    @Autowired
    AmqpTemplate rabbitTemplate;
    //发送信息消息队列
    static final String MSGRABBITQUERENAME="chat.send.msg";
    public void sendMessages(String str) {
        this.rabbitTemplate.convertAndSend(MSGRABBITQUERENAME,str);
    }
}
