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

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void consumerMsg(@Payload byte[] msg) throws Exception {
        String message=new String(msg,"utf-8");
        logger.info("消费端消费消息:"+message);
    }
}
