package com.zhiliag.com.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author:lizhi
 * @Date: 2020/9/12
 * @des:
 **/
@Component
public class RabbitmqConsumer {

    private static final Logger logger= LoggerFactory.getLogger(RabbitmqConsumer.class);

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}",containerFactory = "singleListenerContainer")
    public void consumerMsg(@Payload byte[] msg){
        try{
            String message=new String(msg,"utf-8");
            logger.info("接受到消息为:{}",message);
        }catch (Exception e){
            logger.error("异常",e);
        }
    }
}
