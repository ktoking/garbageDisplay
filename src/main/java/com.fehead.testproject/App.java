package com.fehead.testproject;

import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.fehead.testproject.dao")
//@EnableRabbit//开启注解的rabbitmq
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
