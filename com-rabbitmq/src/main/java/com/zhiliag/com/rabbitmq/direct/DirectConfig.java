package com.zhiliag.com.rabbitmq.direct;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:lizhi
 * @Date: 2020/9/13
 * @des: rabbitmq 直连型消息模型
 **/
@Configuration
public class DirectConfig {

    @Value("${component.directExchange}")
    private String directExchangeName;

    @Value("${component.queue.direct.name1}")
    private String queueDirectName1;

    @Value("${component.queue.direct.name2}")
    private String queueDirectName2;

    @Value("${component.routing-key.direct.keyOne}")
    private String directRoutingKeyOne;

    @Value("${component.routing-key.direct.keyTwo}")
    private String directRoutingKeyTwo;

    @Bean
    public Queue directQueue1(){
        return new Queue(queueDirectName1,true);
    }

    @Bean
    public Queue directQueue2(){
        return new Queue(queueDirectName2,true);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(directExchangeName,true,false);
    }

    @Bean
    public Binding directBindingOne(){
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with(directRoutingKeyOne);
    }

    @Bean
    public Binding directBindingTwo(){
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with(directRoutingKeyTwo);
    }

}
