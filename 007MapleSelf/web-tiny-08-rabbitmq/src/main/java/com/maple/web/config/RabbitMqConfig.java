package com.maple.web.config;

import com.maple.web.dto.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *rabbitmq消息队列的配置与绑定, 订单延迟取消队列
 *@author zxzh
 *@date 2021/5/2
 */
@Configuration
public class RabbitMqConfig {

    /*
    * "订单手动取消"消息交换机
    * */
    @Bean
    DirectExchange orderDirect(){
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange())//队列而非发布订阅者模式
                .durable(true)
                .build();
    }

    /*
     * "订单超时取消"消息交换机
     * */
    @Bean
    DirectExchange orderTtlDirect(){
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ORDER_CANCEL_TTL.getExchange())//队列而非发布订阅者模式
                .durable(true)
                .build();
    }

    /*
    * "订单手动取消"消息队列
    * */
    @Bean
    public Queue orderQueue(){
        return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getName());
    }
    /*
    * "订单超时取消"消息队列  (死信队列)
    * */
    @Bean
    public Queue orderTtlQueue(){
        return QueueBuilder
                .durable(QueueEnum.QUEUE_ORDER_CANCEL_TTL.getName())
                .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_ORDER_CANCEL.getExchange())//到后期转发的交换机
                .withArgument("x-dead-letter-routing-key",QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey())//到后期绑定的路由键
                .build();
    }

    /*
    * 把 "订单手动取消" 队列绑定到交换机上(使用路由键)
    * */
    @Bean
    Binding orderBinding(DirectExchange orderDirect, Queue orderQueue){
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
    }
    /*
    * 把 "订单超时取消" 队列绑定到交换机上(使用路由键)
    * */
    @Bean
    Binding orderTtlBinding(DirectExchange orderTtlDirect, Queue orderTtlQueue){
        return BindingBuilder
                .bind(orderTtlQueue)
                .to(orderTtlDirect)
                .with(QueueEnum.QUEUE_ORDER_CANCEL_TTL.getRouteKey());
    }
}
