package com.extend.demo.mqListener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HelloListener {


    @RabbitListener(queues = "string")
    public void listenHello(String hello){
        System.out.println("监听Mq发送消息："+hello);
    }
    @RabbitListener(queues = "topic.a")
    public void listenTopicA(String msg){
        System.out.println("Start===================topic.a 接收到消息："+msg+"===================End");
    }
    @RabbitListener(queues = "topic.b")
    public void listenTopicB(String msg){
        System.out.println("Start===================topic.b 接收到消息："+msg+"===================End");
    }
    @RabbitListener(queues = "topic.c")
    public void listenTopicC(String msg){
        System.out.println("Start===================topic.c 接收到消息："+msg+"===================End");
    }
}
