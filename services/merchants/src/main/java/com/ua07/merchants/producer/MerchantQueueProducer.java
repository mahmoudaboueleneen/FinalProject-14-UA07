package com.ua07.merchants.producer;

import com.ua07.shared.rabbitmq.RabbitMQConstants;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MerchantQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    public MerchantQueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notifyProductShortage(String productId, int remainingStock) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "SHORTAGE");
        message.put("productId", productId);
        message.put("remainingStock", remainingStock);

        rabbitTemplate.convertAndSend(RabbitMQConstants.MERCHANT_QUEUE, message);
    }
}
