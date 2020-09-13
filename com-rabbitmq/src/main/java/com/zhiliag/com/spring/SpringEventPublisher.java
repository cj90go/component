package com.zhiliag.com.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author:lizhi
 * @Date: 2020/9/12
 * @des: 基于spring实现事件驱动模型
 **/
@Component
public class SpringEventPublisher {

    private static Logger logger= LoggerFactory.getLogger(SpringEventPublisher.class);

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void send(){
        LoginEvent loginEvent=new LoginEvent(this,"127.0.0.1","测试");
        applicationEventPublisher.publishEvent(loginEvent);
        logger.info("发送消息为:{}",loginEvent);
    }
}
