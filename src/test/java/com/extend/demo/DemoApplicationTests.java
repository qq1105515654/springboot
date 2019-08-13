package com.extend.demo;

import com.extend.demo.message.HelloMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private HelloMessage message;

    @Test
    public void contextLoads() {

        for(int i=0; i<10; i++){
            message.sendHello();
        }

    }

    @Test
    public void testFanoutMessage(){

        for(int i=0; i<5; i++){
            message.fanoutSend();
        }
    }

}
