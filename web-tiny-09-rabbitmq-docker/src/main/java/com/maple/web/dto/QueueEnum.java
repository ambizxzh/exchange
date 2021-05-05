package com.maple.web.dto;

import lombok.Getter;

/**
 *枚举 消息队列,注意消息队列间用逗号隔开
 *@author zxzh
 *@date 2021/5/2
 */
@Getter
public enum QueueEnum {
    //交换机名称  队列名称   路由键名称(与队列一般同名)
    //交换机名称的组成(相当于路径)  项目名.分区名.direct.ttl   direct代表队列,而非发布订阅者模式 ttl代表属性名
    /*
    * 消息通知队列
    * */
    QUEUE_ORDER_CANCEL("maple.order.direct", "maple.order.cancel", "maple.order.cancel"),
    /*
    * 消息通知TTL队列
    * */
    QUEUE_ORDER_CANCEL_TTL("maple.order.direct.ttl", "maple.order.cancel.ttl", "maple.order.cancel.ttl");
    /*
    * 交换机名称
    * */
    private String exchange;
    /*
    * 队列名称
    * */
    private String name;
    /*
    * 路由键
    * */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey){
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
