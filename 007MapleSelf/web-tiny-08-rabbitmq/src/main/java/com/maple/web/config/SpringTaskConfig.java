package com.maple.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *定时任务配置
 *@author zxzh
 *@date 2021/5/2
 */
@Configuration
@EnableScheduling//只需要在配置类中添加一个@EnableScheduling注解即可开启SpringTask的定时任务能力
public class SpringTaskConfig {
}
