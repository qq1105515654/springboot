package com.extend.demo.message;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

@Component
public class HelloMessage {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    public void sendHello(){
        rabbitTemplate.convertAndSend("string","Hello RabbitMq");
    }

    public void fanoutSend(){
        Date date=new Date();
        String dateString=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        rabbitTemplate.convertAndSend("fanoutExchange","",dateString);
    }

    public void topicASend(){
        Date date=new Date();
        String dateString=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        //这条消息会被topic.a接收
        rabbitTemplate.convertAndSend("topicExchange","topic.msg",dateString);
    }

    public void topicBSend(){
        Date date=new Date();
        String dateString=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        //这条消息会被 topic.b 接收
        rabbitTemplate.convertAndSend("topicExchange","topic.good.msg",dateString);
    }

    public void topicCSend(){
        Date date=new Date();
        String dateString=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        //这条消息会被 topic.c接收
        rabbitTemplate.convertAndSend("topicExchange","topic.snh.z",dateString);
    }
}
