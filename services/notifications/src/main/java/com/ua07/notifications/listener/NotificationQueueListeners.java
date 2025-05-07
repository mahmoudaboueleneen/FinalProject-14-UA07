package com.ua07.notifications.listener;

import com.ua07.notifications.config.QueueConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationQueueListeners {

    @RabbitListener(queues = QueueConfig.TRANSACTION_QUEUE)
    public void handleTransactionEvent(Map<String, Object> message) {
        String orderId = (String) message.get("orderId");
        String type = (String) message.get("type");
        String msg = (String) message.get("message");

        switch (type) {
            case "ORDER_CONFIRMED":
                System.out.println("[NOTIFY] Order " + orderId + " confirmed: " + msg);
                break;
            case "STATUS_UPDATED":
                System.out.println("[NOTIFY] Order " + orderId + " status update: " + msg);
                break;
            default:
                System.out.println("[NOTIFY] Order " + orderId + ": " + msg);
        }
    }

    @RabbitListener(queues = QueueConfig.MERCHANT_QUEUE)
    public void handleMerchantShortage(Map<String, Object> message) {
        if ("SHORTAGE".equals(message.get("type"))) {
            String productId = (String) message.get("productId");
            int stock = (Integer) message.get("remainingStock");
            System.out.println("[ALERT] Product " + productId + " is low on stock: only " + stock + " left!");
        }
    }
}
