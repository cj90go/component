package com.zhiliag.com.rabbitmq.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author:lizhi
 * @Date: 2020/9/12
 * @des: 基于FanoutExchange 广播消费模型
 **/
@Configuration
public class FanoutConfig {

    @Value("${spring.rabbitmq.queue.fanout-name1}")
    private String queueName1;

    @Value("${spring.rabbitmq.queue.fanout-name2}")
    private String queueName2;

    @Value("${component.fanoutExchange}")
    private String fanoutExchange;


    @Bean(name="fanoutTest1")
    public Queue fanoutTest1(){
        return new Queue(queueName1);
    }

    @Bean(name = "fanoutTest2")
    public Queue fanoutTest2(){
        return new Queue(queueName2);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(fanoutExchange);
    }

    @Bean
    public Binding fanoutBindingTest1(){
        return BindingBuilder.bind(fanoutTest1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBindingTest2(){
        return BindingBuilder.bind(fanoutTest2()).to(fanoutExchange());
    }
}
