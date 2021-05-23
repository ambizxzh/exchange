package com.maple.web.direct;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DirectSender {
    @Autowired
    private AmqpTemplate amqpTemplate;
    private static final String exchangeName = "exchange.direct";
    private final String[] bindingKeys = {"red", "green", "blue"};

    public void send(int index){
        StringBuilder sb = new StringBuilder("Hello to ");
        String key = bindingKeys[index % 3];
        sb.append(key).append(" ");
        sb.append(index + 1);
        String message = sb.toString();
        amqpTemplate.convertAndSend(exchangeName, key, message);
        log.info("[x] Sent '{}'", message);
    }
}
