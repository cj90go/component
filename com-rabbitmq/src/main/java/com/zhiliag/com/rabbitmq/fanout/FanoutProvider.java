package com.zhiliag.com.rabbitmq.fanout;

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
 * @Date: 2020/9/12
 * @des: 基于FanoutExchange消费模型下的生产者
 **/
@Component
public class FanoutProvider {

    private static final Logger logger = LoggerFactory.getLogger(FanoutProvider.class);

    @Autowired
    ObjectMapper objectMapper;

    @Value("${component.fanoutExchange}")
    String fanoutExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(EventInfo message) {
        if (!StringUtils.isEmpty(message)) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(fanoutExchange);
                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message)).build();
                rabbitTemplate.convertAndSend(msg);
                logger.info("消息模型fanout-Exchange-生产者-发送消息:{}",message);
            } catch (Exception e) {
                logger.error("消息模型fanout-Exchange-生产者-发送消息:{}",message,e.fillInStackTrace());
            }
        }
    }
}
