package com.extend.demo.common.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;


@Configuration
public class MessageConfig {


    private final static String STRING = "string";

    /**
     * 普通工作模式，生产消费
     *
     * @return
     */
    @Bean
    public Queue string() {
        return new Queue(STRING);
    }


    /**
     * fanout  模式  fanout 属于广播模式，只要跟他绑定的队列都会通知并且接受消息
     */
    //================fanout 模式================================
    @Bean
    public Queue fanoutA() {
        return new Queue("fanout.a");
    }

    @Bean
    public Queue fanoutB() {
        return new Queue("fanout.b");
    }

    @Bean
    public Queue fanoutC() {
        return new Queue("fanout.c");
    }

    /**
     * 定义一个 fanout交换器
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 将定义的fanout 与交换器绑定
     */
    @Bean
    public Binding bindingExchangeWithA() {
        return BindingBuilder.bind(fanoutA()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingExchangeWithB() {
        return BindingBuilder.bind(fanoutB()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingExchangeWithC() {
        return BindingBuilder.bind(fanoutC()).to(fanoutExchange());
    }

    //================topic模式================

    /**
     * topic 模式
     */
    @Bean
    public Queue topicA() {
        return new Queue("topic.a");
    }

    @Bean
    public Queue topicB() {
        return new Queue("topic.b");
    }

    @Bean
    public Queue topicC() {
        return new Queue("topic.c");
    }

    /**
     * 定义一个topic 交换器
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    // topicA 的key 为 topic.msg 那么他只会接收包含 topic.msg的消息
    @Bean
    public Binding bindingTopicExchangeWithA() {
        return BindingBuilder.bind(topicA()).to(topicExchange()).with("topic.msg");
    }

    //topicB 的 key 为topic.# 那么他只会接收topic 开头的消息
    @Bean
    Binding bindingTopicExchangeWithB() {
        return BindingBuilder.bind(topicB()).to(topicExchange()).with("topic.#");
    }

    //topicC的 key 为topic.*.z 那么他只会接收 topic.B.z 这样的消息
    @Bean
    Binding bindingTopicExchangeWithC() {
        return BindingBuilder.bind(topicC()).to(topicExchange()).with("topic.*.z");
    }


    @Bean
    public Queue directQueue() {
        return new Queue("direct1" , true);
    }

    @Bean
    DirectExchange directExchange() {
        DirectExchange exchange = new DirectExchange("directExchange" , true, false);
        return exchange;
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("directExchangeRouting");
    }

}
