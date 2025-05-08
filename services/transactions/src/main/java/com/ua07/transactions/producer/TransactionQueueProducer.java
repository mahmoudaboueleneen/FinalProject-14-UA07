package com.ua07.transactions.producer;

import com.ua07.shared.rabbitmq.RabbitMQConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    public TransactionQueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notifyOrderEvent(String orderId, String eventType, String message) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("orderId", orderId);
        payload.put("type", eventType); // e.g. ORDER_CONFIRMED, STATUS_UPDATED
        payload.put("message", message);

        rabbitTemplate.convertAndSend(RabbitMQConstants.TRANSACTION_QUEUE, payload);
    }

}
