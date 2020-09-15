package com.zhiliag.com.rabbitmq.konwledge.auto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:lizhi
 * @Date: 2020/9/15
 * @des: 基于自动确认消费模式实战
 **/
@Configuration
public class RabbitAutoConfig {

    private static final Logger logger= LoggerFactory.getLogger(RabbitAutoConfig.class);

    //自动装配Rabbitmq的链接工厂实例
    @Autowired
    private ConnectionFactory connectionFactory;

    //自动装配消息监听器所在的容器工厂配置实例
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Value("${component.queue.auto.autoQueue}")
    private String autoQueue;

    @Value("${component.autoExchange}")
    private String  autoExchangeName;

    @Value("${component.routing-key.auto.routingKey}")
    private String autoRoutingKey;

    @Bean("autoQueue")
    public Queue autoQueue(){
        return new Queue(autoQueue,true);
    }

    @Bean("autoExchange")
    public DirectExchange directExchange(){
        return new DirectExchange(autoExchangeName,true,false);
    }

    @Bean
    public Binding autoBinding(){
        return BindingBuilder.bind(autoQueue()).to(directExchange()).with(autoRoutingKey);
    }

    /**
     * 单一消费者实例的配置
     * @return
     */
    @Bean("singleListenerContainerAuto")
    public SimpleRabbitListenerContainerFactory singleListenerContainer(ConnectionFactory connectionFactory){
        //1.定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
        //2.设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        //3.设置消息在传输中的格式
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //4.设置并发消费者实例的初始数量
        factory.setConcurrentConsumers(1);
        //5.设置并发消费者实例的最大数量
        factory.setMaxConcurrentConsumers(1);
        //6.设置并发消费者实例中每个实例拉取的消息数量
        factory.setPrefetchCount(1);
        //7.设置确认消费模式为自动消费模式
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;

    }


}
