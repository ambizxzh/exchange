package com.maple.web.component;

import com.maple.web.dto.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelOrderSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderSender.class);
        @Autowired
        private AmqpTemplate amqpTemplate;

        public void sendMessage(Long orderId, final long delayTime){
            amqpTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_PLUGIN_CANCEL.getExchange(),
                    QueueEnum.QUEUE_ORDER_PLUGIN_CANCEL.getRouteKey(), orderId,
                    new MessagePostProcessor() {
                        @Override
                        public Message postProcessMessage(Message message) throws AmqpException {
                            message.getMessageProperties().setHeader("x-delay", delayTime);
                            return message;
                        }
                    });

            LOGGER.info("send delay message orderId:{}", orderId);
        }

}
