package com.ua07.notifications.factory;

import com.ua07.notifications.models.Notification;
import com.ua07.notifications.models.NotificationType;

import java.time.Instant;
import java.util.UUID;

public class NotificationFactory {

    public static Notification createCustomerNotification(UUID userId, String message, NotificationType type) {
        Notification notification = new Notification();
        notification.setId(UUID.randomUUID().toString());
        notification.setTimestamp(Instant.now());
        notification.setIsRead(false);
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setType(type);
        return notification;
    }

    public static Notification createMerchantNotification(UUID userId, String productIdInShortage, int currentCount, int threshold) {
        Notification notification = new Notification();
        notification.setId(UUID.randomUUID().toString());
        notification.setTimestamp(Instant.now());
        notification.setIsRead(false);
        notification.setUserId(userId);
        notification.setProductIdInShortage(productIdInShortage);
        notification.setCurrentCount(currentCount);
        notification.setThreshold(threshold);
        return notification;
    }

}