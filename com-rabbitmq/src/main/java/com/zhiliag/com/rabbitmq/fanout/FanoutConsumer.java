package com.zhiliag.com.rabbitmq.fanout;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class FanoutConsumer {

    private static final Logger logger= LoggerFactory.getLogger(FanoutConsumer.class);

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "${spring.rabbitmq.queue.fanout-name1}")
    public void consumerFanoutOne(@Payload byte[] bytes){
        try{
            EventInfo eventInfo=objectMapper.readValue(bytes,EventInfo.class);
            logger.info("消息模型-fanoutExchange-消费端1-接收到的消息为:{}",eventInfo);
        }catch (Exception e){
            logger.error("消费模型-fanoutExchange-消费端1-接受到的消息异常",e.fillInStackTrace());
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.fanout-name2}")
    public void consumerFanoutTwo(@Payload byte[] bytes){
        try{
            EventInfo eventInfo=objectMapper.readValue(bytes,EventInfo.class);
            logger.info("消息模型-fanoutExchange-消费端1-接收到的消息为:{}",eventInfo);
        }catch (Exception e){
            logger.error("消费模型-fanoutExchange-消费端2-接受到的消息异常",e.fillInStackTrace());
        }
    }
}
