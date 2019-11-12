//package com.fehead.testproject.service.impl.impl;
//
//import com.fehead.testproject.dataobject.UserDO;
//import com.fehead.testproject.service.impl.UserService;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserserviceImpl implements UserService {
//
//    @RabbitListener(queues = "kaikai.news")
//    public void receive(UserDO userDO){
//        System.out.println("received! Meg:" + userDO);
//    }
//    @RabbitListener(queues = "kaikai")
//    public void receive2(Message message){
//        System.out.println(message.getMessageProperties());
//        System.out.println(message.getBody());
//
//    }
//
//}
