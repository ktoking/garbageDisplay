//package com.fehead.testproject.test;
//
//import com.fehead.testproject.dataobject.UserDO;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SpringBootAmqpAppTests {
//
//    @Autowired
//     RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    AmqpAdmin amqpAdmin;
//
//    @Test
//    public void createExchange(){
//        //创建一个交换器
//        amqpAdmin.declareExchange(new DirectExchange("aijiajia"));
//        //创建一个队列
//        amqpAdmin.declareQueue(new Queue("aijiajia.queue",true));
//        //创建绑定规则
//        amqpAdmin.declareBinding(new Binding("aijiajia.queue",Binding.DestinationType.QUEUE,"aijiajia.exchange","amqp.haha",null));
//
//    }
//
//    @Test
//    public void contextLoads(){
//        //Message需要自己构造一个;定义消息体的内容和消息头
//        // rabbitTemplate.send(exchange,routeKey,message);
//        //object默认当成消息体,只需要传入要发送的对象,自动化序列发给irabbitmq
//        //rabbitTemplate.convertAndSend(exchange,routeKey,object);
//        Map<String,Object> map=new HashMap<>();
//        map.put("name1",Arrays.asList("dasd",1323,"dsada"));
//        //单播,对象经序列化后发出去
//        rabbitTemplate.convertAndSend("exchange.direct","kaikai.news",map);
//    }
//
//    @Test
//    public void receive(){
//        Object mes= rabbitTemplate.receiveAndConvert("kaikai.data");
//        System.out.println(mes.getClass());
//        System.out.println(mes);
//    }
//
//    /**
//     * 广播模式
//     */
//    @Test
//    public void sending(){
//        UserDO userDO=new UserDO();
//        userDO.setId(2);
//        userDO.setUserEmail("aijiajia@qq.com");
//        rabbitTemplate.convertAndSend("exchange.fanout","",userDO);
//    }
//}
