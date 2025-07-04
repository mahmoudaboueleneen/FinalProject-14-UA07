package com.ua07.transactions.producer;

import com.ua07.shared.rabbitmq.RabbitMQConstants;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    public TransactionQueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notifyOrderEvent(String userId, String eventType, String message) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userId);
        payload.put("type", eventType); // e.g. ORDER_CONFIRMED, STATUS_UPDATED
        payload.put("message", message);

        rabbitTemplate.convertAndSend(RabbitMQConstants.TRANSACTION_QUEUE, payload);
    }
}
