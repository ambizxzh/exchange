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
    * 插件消息队列
    * */
    QUEUE_ORDER_PLUGIN_CANCEL("maple.order.direct.plugin", "maple.order.cancel.plugin", "maple.order.cancel.plugin");
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
