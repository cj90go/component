package com.zhiliag.com.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.zhiliag.com.rabbitmq.direct.DirectProvider;
import com.zhiliag.com.rabbitmq.fanout.EventInfo;
import com.zhiliag.com.rabbitmq.fanout.FanoutProvider;
import com.zhiliag.com.rabbitmq.provider.RabbitmqProvider;
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

    @Test
    public void test3(){
        EventInfo eventInfo=new EventInfo(1,"test","test","test");
        EventInfo eventInfo1=new EventInfo(2,"test2","test2","test2");
        directProvider.sendMsgDirectOne(eventInfo);
        directProvider.sendMsgDirectTwo(eventInfo1);
    }
}
