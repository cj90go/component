package com.zhiliag.com.rabbitmq;

import com.zhiliag.com.spring.SpringEventPublisher;
import org.junit.Test;
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
public class EventTest {

    @Autowired
    private SpringEventPublisher springEventPublisher;

    @Test
    public void test() throws Exception{
        springEventPublisher.send();
    }
}
