package com.zhiliag.com.rabbitmq;

import com.zhiliag.com.rabbitmq.direct.DirectProvider;
import com.zhiliag.com.rabbitmq.config.EventInfo;
import com.zhiliag.com.rabbitmq.fanout.FanoutProvider;
import com.zhiliag.com.rabbitmq.config.RabbitmqProvider;
import com.zhiliag.com.rabbitmq.topic.TopicProvider;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author:lizhi
 * @Date: 2020/9/12
 * @des:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitmqTest {

    private static final Logger logger= LoggerFactory.getLogger(RabbitmqTest.class);


    @Autowired
    RabbitmqProvider rabbitmqProvider;

    @Autowired
    FanoutProvider fanoutProvider;

    @Autowired
    DirectProvider directProvider;

    @Autowired
    TopicProvider topicProvider;

    private String routingKeyOne="test.queue.topic.routing.java.key";//基于通配符(*)

    private String routingKeyTwo="test.queue.topic.routing.python.key";//基于通配符(*)

    private String routingKeyThree="test.queue.topic.routing.key";//基于通配符(#)

    @Test
    public  void test1() throws Exception{
        String msg="test";
        rabbitmqProvider.send(msg);
    }

    /**
     * 测试广播消息模型(FanoutExchange)
     * @throws Exception
     */
    @Test
    public  void test2() throws Exception{
        EventInfo eventInfo=new EventInfo(1,"test","test","test");
        fanoutProvider.sendMsg(eventInfo);
    }

    /**
     * 测试基于直连型模型(DirectExchange)
     */
    @Test
    public void test3(){
        EventInfo eventInfo=new EventInfo(1,"test","test","test");
        EventInfo eventInfo1=new EventInfo(2,"test2","test2","test2");
        directProvider.sendMsgDirectOne(eventInfo);
        directProvider.sendMsgDirectTwo(eventInfo1);
    }

    /**
     * 测试基于发布-主题-订阅消息模型(TopicExchange)
     */
    @Test
    public void test4(){
        EventInfo eventInfo=new EventInfo(1,"test","test","test");
        topicProvider.sendMsgTopic(routingKeyOne,eventInfo);
        topicProvider.sendMsgTopic(routingKeyTwo,eventInfo);
        topicProvider.sendMsgTopic(routingKeyThree,eventInfo);
    }
}
