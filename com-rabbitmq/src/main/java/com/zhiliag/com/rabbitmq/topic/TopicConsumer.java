package com.zhiliag.com.rabbitmq.topic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhiliag.com.rabbitmq.config.EventInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author:lizhi
 * @Date: 2020/9/13
 * @des:
 **/
@Component
public class TopicConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TopicConsumer.class);

    @Autowired
    ObjectMapper objectMapper;


    @RabbitListener(queues = "${component.queue.topic.name1}")
    public void topicConsumerMsgOne(@Payload byte[] bytes) {
        try {
            logger.info("基于topic模型通配符为*");
            EventInfo eventInfo = objectMapper.readValue(bytes, EventInfo.class);
            logger.info("基于TopicExchange模型-*-消费者-监听到的消息为:{}", eventInfo);
        } catch (Exception e){
            logger.error("基于TopicExchange模型-*-消费者-监听到的消息异常"+e.fillInStackTrace());
        }
    }

    @RabbitListener(queues = "${component.queue.topic.name2}")
    public void topicConsumerMsgTwo(@Payload byte[] bytes) {
        try {
            logger.info("基于topic模型通配符为#");
            EventInfo eventInfo = objectMapper.readValue(bytes, EventInfo.class);
            logger.info("基于TopicExchange模型-#-消费者-监听到的消息为:{}", eventInfo);
        } catch (Exception e){
            logger.error("基于TopicExchange模型-#-消费者-监听到的消息异常"+e.fillInStackTrace());
        }
    }
}
