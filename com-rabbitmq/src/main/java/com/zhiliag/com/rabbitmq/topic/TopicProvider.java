package com.zhiliag.com.rabbitmq.topic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhiliag.com.rabbitmq.config.EventInfo;
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
 * @Date: 2020/9/13
 * @des: 基于topicExchange消费模型的生产者
 **/
@Component
public class TopicProvider {

    private static final Logger logger= LoggerFactory.getLogger(TopicProvider.class);

    @Autowired
    ObjectMapper objectMapper;

    @Value("${component.topicExchange}")
    private String topicExchangeName;

    @Value("${component.routing-key.topic.keyOne}")
    private String topicRoutingKeyOne;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMsgTopic(String routingKey,EventInfo eventInfo){
        if (!StringUtils.isEmpty(eventInfo)){
            try{
                //1.设置消息的传输格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //2.设置交换机
                rabbitTemplate.setExchange(topicExchangeName);
                //3.设置路由
                rabbitTemplate.setRoutingKey(routingKey);
                //4.创建消息
                Message message= MessageBuilder.withBody(objectMapper.writeValueAsBytes(eventInfo)).build();
                //5.发送消息
                rabbitTemplate.convertAndSend(message,message1 -> message);
                logger.info("消息模型TopicExchange-生产者—发送消息:{},路由:{}",eventInfo,routingKey);
            }catch (Exception e){
                logger.error("消息模型TopicExchange-生产者—发送消息异常"+e.fillInStackTrace());
            }
        }
    }
}
