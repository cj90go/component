# component
分布式中间件技术实战:基于springboot集成redis,mysql,activemq，redission等技术

一.分布式锁的实现方式: 1.mysql 2.redis 3.zookeeper 4.redission(基于redis下成熟的产品解决方案)
mysql:
    案例:银行提现案例
    方案:springboot基于mysql下，实现悲观锁和乐观锁
 测试方式: 1.postman调用接口正常
          2.基于jmeter模拟并发下,接口满足数据一致性和业务的需求

redis:



二.SpringBoot整合rabbitmq:
   package: spring 基于Spring事件驱动模型,实现异步发送消息
   rabbitmq: springboot整合rabbitmq实现消息发送和接收
   
    