package com.maple.web.direct;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitmqConfig {
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("exchange.direct");
    }
    @Bean
    public Queue directQueue1(){
        return new AnonymousQueue();
    }
    @Bean
    public Queue directQueue2(){
        return new AnonymousQueue();
    }
    /*
    * exchange --red-- queue1
    * exchange --green-- queue1
    * exchange --blue-- queue2
    * exchange --red-- queue2
    * */
    @Bean
    public Binding directBinding1a(DirectExchange directExchange, Queue directQueue1){//为了实现绑定,必须使用上面已经定义的交换机(方法)名和队列(方法)名
        return BindingBuilder.bind(directQueue1).to(directExchange).with("red");
    }
    @Bean
    public Binding directBinding1b(DirectExchange directExchange, Queue directQueue1){
        return BindingBuilder.bind(directQueue1).to(directExchange).with("green");
    }
    @Bean
    public Binding directBinding2a(DirectExchange directExchange, Queue directQueue2){
        return BindingBuilder.bind(directQueue2).to(directExchange).with("red");
    }
    @Bean
    public Binding directBinding2b(DirectExchange directExchange, Queue directQueue2){
        return BindingBuilder.bind(directQueue2).to(directExchange).with("blue");
    }



}



