package com.zhiliag.com.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * @author:lizhi
 * @Date: 2020/9/12
 * @des: 基于rabbitmq消息发送者
 **/
@Component
public class RabbitmqProvider {

    private static final Logger logger= LoggerFactory.getLogger(RabbitmqProvider.class);


    @Value("${spring.rabbitmq.template.exchange}")
    private String exchangeName;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message){
        if (!StringUtils.isEmpty(message)){
            try{
                //1.定义消息传输的格式为JSON字符串格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //2.指定消模型中的交换机
                rabbitTemplate.setExchange(exchangeName);
                //3.指定消息路由
                rabbitTemplate.setRoutingKey(routingKey);
                //4.将字符串转换成待发消息,即二进制流
                Message msg= MessageBuilder.withBody(message.getBytes("utf-8")).build();
                //5.转发并发送消息
                rabbitTemplate.convertAndSend(msg);
                logger.info("发送消息:{}",message);
            }catch (Exception e){
                logger.error("发送消息异常,{}",message,e.fillInStackTrace());
            }
        }
    }
}
