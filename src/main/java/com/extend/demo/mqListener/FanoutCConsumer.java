package com.extend.demo.mqListener;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.c")
public class FanoutCConsumer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RabbitHandler
    public void recieved(String msg){
        System.out.println("Start===============接收到 fanout.c 消息："+msg+"===============End");
    }
}
