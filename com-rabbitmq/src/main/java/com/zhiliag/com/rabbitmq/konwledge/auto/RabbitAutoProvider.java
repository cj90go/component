package com.zhiliag.com.rabbitmq.konwledge.auto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhiliag.com.rabbitmq.config.EventInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * @author:lizhi
 * @Date: 2020/9/15
 * @des:
 **/
@Component
public class RabbitAutoProvider {

    private static final Logger logger= LoggerFactory.getLogger(RabbitAutoProvider.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${component.autoExchange}")
    private String  autoExchangeName;

    @Value("${component.routing-key.auto.routingKey}")
    private String autoRoutingKey;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @RabbitHandler
    public void sendAutoMsg(EventInfo eventInfo){
        try{
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(autoExchangeName);
            rabbitTemplate.setRoutingKey(autoRoutingKey);
            Message message=MessageBuilder.withBody(objectMapper.writeValueAsBytes(eventInfo))
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            rabbitTemplate.convertAndSend(message);
            logger.info("基于Auto机制-生产者发送消息内容{}",eventInfo);
        }catch (Exception e){
            logger.info("基于Auto机制-生产者发送消息异常{}",e.fillInStackTrace());
        }
    }

}
