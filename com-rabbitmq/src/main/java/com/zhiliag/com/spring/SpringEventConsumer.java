package com.zhiliag.com.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @author:lizhi
 * @Date: 2020/9/12
 * @des: 基于Spring事件驱动模型消费端
 **/
@Component
@EnableAsync //开启异步
public class SpringEventConsumer implements ApplicationListener<LoginEvent> {

    private static final Logger logger= LoggerFactory.getLogger(SpringEventConsumer.class);

    @Override
    @Async
    public void onApplicationEvent(LoginEvent loginEvent) {
        logger.info("接收到消息为:{}",loginEvent);
    }
}
