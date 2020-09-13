package com.zhiliag.com.rabbitmq.direct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhiliag.com.rabbitmq.fanout.EventInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author:lizhi
 * @Date: 2020/9/13
 * @des:
 **/
@Component
public class DirectConsumer {

    private static final Logger logger= LoggerFactory.getLogger(DirectConsumer.class);

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 监听并消费队列中的消息-directExchange-one
     * @param bytes
     */
    @RabbitListener(queues = "${component.queue.direct.name1}")
    public void consumerDirectMsgOne(@Payload byte[] bytes){
        try{
            EventInfo eventInfo=objectMapper.readValue(bytes,EventInfo.class);
            logger.info("消息模型directExchange-one-消费者-监听到消费消息:{}",eventInfo);
        }catch (Exception e){
            logger.error("消息模型directExchange-one-消费者-监听到消费消息异常"+e.fillInStackTrace());
        }
    }

    /**
     *监听并消费队列中的消息-directExchange-two
     * @param bytes
     */
    @RabbitListener(queues = "${component.queue.direct.name2}")
    public void consumerDirectMsgTwo(@Payload byte[] bytes){
        try{
            EventInfo eventInfo=objectMapper.readValue(bytes,EventInfo.class);
            logger.info("消息模型directExchange-two-消费者-监听到消费消息:{}",eventInfo);
        }catch (Exception e){
            logger.error("消息模型directExchange-two-消费者-监听到消费消息异常"+e.fillInStackTrace());
        }
    }
}
