package com.extend.demo.mqListener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectConsumer {

    @RabbitListener(queues = "direct1")
    public void directListener(String message) {
        System.out.println("Start=======================================" + message + "=======================================End");
    }
}
