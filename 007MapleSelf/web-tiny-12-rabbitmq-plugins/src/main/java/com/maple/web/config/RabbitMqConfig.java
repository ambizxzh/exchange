package com.maple.web.config;

import com.maple.web.dto.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *rabbitmq消息队列的配置与绑定, 订单延迟取消队列
 *@author zxzh
 *@date 2021/5/2
 */
@Configuration
public class RabbitMqConfig {
    /*
    * 订单延迟插件消息队列所绑定的交换机
    * */
    @Bean
    CustomExchange orderPluginDirect(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(QueueEnum.QUEUE_ORDER_PLUGIN_CANCEL.getExchange(), "x-delayed-message", true, false, args);
    }

    /*
    * 订单延迟插件消息队列
    * */
    @Bean
    public  Queue orderPluginQueue(){
        return new Queue(QueueEnum.QUEUE_ORDER_PLUGIN_CANCEL.getName());
    }

    /*
    * 队列与交换机绑定,生成绑定项作为路由表的一项映射
    * */
    @Bean
    public Binding orderPluginBinding(CustomExchange orderPluginDirect, Queue orderPluginQueue){
        return BindingBuilder
                .bind(orderPluginQueue)
                .to(orderPluginDirect)
                .with(QueueEnum.QUEUE_ORDER_PLUGIN_CANCEL.getRouteKey())
                .noargs();
    }
}
