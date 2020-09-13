package com.zhiliag.com.rabbitmq.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:lizhi
 * @Date: 2020/9/13
 * @des:
 **/
@Configuration
public class TopicConfig {

    @Value("${component.topicExchange}")
    private String topicExchangeName;

    @Value("${component.queue.topic.name1}")
    private String queueNameOne;

    @Value("${component.queue.topic.name2}")
    private String queueNameTwo;

    @Value("${component.routing-key.topic.keyOne}")
    private String topicRoutingKeyOne;

    @Value("${component.routing-key.topic.keyTwo}")
    private String topicRoutingKeyTwo;

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(topicExchangeName,true,false);
    }

    @Bean
    public Queue topicQueueOne(){
        return new Queue(queueNameOne,true);
    }

    @Bean
    public Queue topicQueueTwo(){
        return new Queue(queueNameTwo,true);
    }

    @Bean
    public Binding topicBindingOne(){
        return BindingBuilder.bind(topicQueueOne()).to(topicExchange()).with(topicRoutingKeyOne);
    }

    @Bean
    public Binding topicBindingTwo(){
        return BindingBuilder.bind(topicQueueTwo()).to(topicExchange()).with(topicRoutingKeyTwo);
    }
}
