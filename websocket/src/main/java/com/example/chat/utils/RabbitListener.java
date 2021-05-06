package com.example.chat.utils;


import com.example.chat.websockets.ChatSocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;

@Component
@org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "chat.send.msg")
public class RabbitListener {

    static RabbitProducer rabbitProducer;

    @Autowired
    public void setRabbitProducer(RabbitProducer rabbitProducer) {
        RabbitListener.rabbitProducer = rabbitProducer;
    }

    @RabbitHandler
    public void recieved(String jsonStr, Channel channel, Message message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RabbitMqDto rabbitMqDto = objectMapper.readValue(jsonStr, RabbitMqDto.class);

        Session session = ChatSocket.getSessionMapByKey(rabbitMqDto.getKey());//对方的session
        if (session != null) {
            rabbitMqDto.setKey(null);
            session.getAsyncRemote().sendText(jsonStr);

            //deliveryTag:该消息的index   ，bool：是否批量.true:将一次性ack所有小于deliveryTag的消息。
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);//消息队列删除该消息
        } else {
            //deliveryTag:该消息的index，multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
            // requeue：被拒绝的是否重新入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, false);
            rabbitProducer.sendMessages(jsonStr);
        }
    }
}

