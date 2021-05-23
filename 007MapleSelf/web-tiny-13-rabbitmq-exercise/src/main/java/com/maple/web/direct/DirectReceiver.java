package com.maple.web.direct;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
public class DirectReceiver {
    @RabbitListener(queues = "#{directQueue1.name}")
    public void receive1(String in){
        receive(in, 1);
    }
    @RabbitListener(queues = "#{directQueue2.name}")
    public void receive2(String  in){
        receive(in, 2);
    }
    private void receive(String in, int receiver){
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("instance {} [x] Received '{}'", receiver, in);
        doWork(in);
        watch.stop();
        log.info("instance {} [x] Done in {}s", receiver, watch.getTotalTimeSeconds());

    }
    private void doWork(String in){
        for(char ch : in.toCharArray()){
            if(ch == '.'){
                ThreadUtil.sleep(1000);
            }
        }
    }

}
