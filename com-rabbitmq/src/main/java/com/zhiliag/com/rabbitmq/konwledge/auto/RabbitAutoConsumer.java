package com.zhiliag.com.rabbitmq.konwledge.auto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.String;
import com.zhiliag.com.rabbitmq.config.EventInfo;
import com.zhiliag.com.rabbitmq.direct.DirectConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author:lizhi
 * @Date: 2020/9/15
 * @des:
 **/
@Component
public class RabbitAutoConsumer {
    private static final Logger logger= LoggerFactory.getLogger(DirectConsumer.class);

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 监听并消费队列中的消息-directExchange-one
     * @param message
     */
    @RabbitListener(queues = "${component.queue.auto.autoQueue}",containerFactory = "singleListenerContainerAuto")
    public void consumerDirectMsgOne(Message message){
        try{
            logger.info("基于Auto机制-消费者接收消息内容{}",message.getPayload());
        }catch (Exception e){
            logger.error("基于Auto机制-生产者接收消息异常"+e.fillInStackTrace());
        }
    }
}
