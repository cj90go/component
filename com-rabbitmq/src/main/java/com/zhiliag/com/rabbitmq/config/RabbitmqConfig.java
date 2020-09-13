package com.zhiliag.com.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
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
 * @Date: 2020/9/12
 * @des:
 **/
@Configuration
public class RabbitmqConfig {

    private static final Logger logger= LoggerFactory.getLogger(RabbitmqConfig.class);

    //自动装配Rabbitmq的链接工厂实例
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    //自动装配消息监听器所在的容器工厂配置实例
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Value("${spring.rabbitmq.queue.name}")
    private String queue;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;

    //定义队列
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    //创建交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchange,true,false);
    }

    //创建绑定
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(routingKey);
    }


    /**
     * 单一消费者实例的配置
     * @return
     */
    @Bean("singleListenerContainer")
    public SimpleRabbitListenerContainerFactory singleListenerContainer(){
        //1.定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
        //2.设置容器工厂所用的实例
        factory.setConnectionFactory(cachingConnectionFactory);
        //3.设置消息在传输中的格式
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //4.设置并发消费者实例的初始数量
        factory.setConcurrentConsumers(1);
        //5.设置并发消费者实例的最大数量
        factory.setMaxConcurrentConsumers(1);
        //6.设置并发消费者实例中每个实例拉取的消息数量
        factory.setPrefetchCount(1);
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        return factory;

    }

    /**
     * 多个消费者实例的配置:用于高并发下业务场景的配置
     * @return
     */
    @Bean("multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        //1.定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
        //2.设置容器工厂所用的实例
        factoryConfigurer.configure(factory,cachingConnectionFactory);
        //3.设置消息在传输中的格式
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //4.设置消息的确认消费模式 NONE:无需确认;AUTO:自动确认;MANUAL:手动确认
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        //5.设置并发消费者的初始数量
        factory.setConcurrentConsumers(10);
        //6.设置并发消费者的最大数量
        factory.setMaxConcurrentConsumers(100);
        //7.设置并发消费者实例中每个实例拉取的消息数量
        factory.setPrefetchCount(10);
        return factory;
    }

    @Bean("testRabbitmq")
    public RabbitTemplate rabbitTemplate(){
        //1.设置发送消息后确认 (ack设置为true)
        cachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        //2.发送消息后返回确认信息
        cachingConnectionFactory.setPublisherReturns(true);
        //3.构造发送消息组件实例对象
        RabbitTemplate rabbitTemplate=new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMandatory(true);
        //4.发送消息后，如果发送成功,则输出"发送消息成功"的反馈信息
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                logger.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,b,s);
            }
        });
        //5.发送消息后,如果发送失败,则输出"消息发送失败-消息丢失"的反馈消息
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                logger.info("发送消息丢失: exchange({}),route({}),replyCode({}),replyText({}),message:{}",
                        exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }
}
