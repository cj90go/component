package com.zhiliag.com.rabbitmq.direct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhiliag.com.rabbitmq.fanout.EventInfo;
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
 * @des:
 **/
@Component
public class DirectProvider {

    private static final Logger logger= LoggerFactory.getLogger(DirectProvider.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${component.directExchange}")
    private String exchangeName;

    @Value("${component.routing-key.direct.keyOne}")
    private String routingKeyName;

    @Value("${component.routing-key.direct.keyTwo}")
    private String routingKeyName2;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMsgDirectOne(EventInfo eventInfo){
        if (!StringUtils.isEmpty(eventInfo)){
            try{
                //1.设置消息传输的格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //2.设置交换机
                rabbitTemplate.setExchange(exchangeName);
                //3.设置路由1
                rabbitTemplate.setRoutingKey(routingKeyName);
                //4.构建消息
                Message message= MessageBuilder.withBody(objectMapper.writeValueAsBytes(eventInfo)).build();
                //5.发送消息
                rabbitTemplate.convertAndSend(message,message1 -> message);
                logger.info("消息模型DirectExchange-One-生产者-发送消息{}",eventInfo);
            }catch (Exception e){
                logger.error("消息模型DirectExchange-One-生产者-发送消息异常"+e.fillInStackTrace());
            }
        }
    }

    public void sendMsgDirectTwo(EventInfo eventInfo){
        if (!StringUtils.isEmpty(eventInfo)){
            try{
                //1.设置消息传输的格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //2.设置交换机
                rabbitTemplate.setExchange(exchangeName);
                //3.设置路由1
                rabbitTemplate.setRoutingKey(routingKeyName2);
                //4.构建消息
                Message message= MessageBuilder.withBody(objectMapper.writeValueAsBytes(eventInfo)).build();
                //5.发送消息
                rabbitTemplate.convertAndSend(message,message1 -> message);
                logger.info("消息模型DirectExchange-Two-生产者-发送消息{}",eventInfo);
            }catch (Exception e){
                logger.error("消息模型DirectExchange-Two-生产者-发送消息异常"+e.fillInStackTrace());
            }
        }
    }
}
