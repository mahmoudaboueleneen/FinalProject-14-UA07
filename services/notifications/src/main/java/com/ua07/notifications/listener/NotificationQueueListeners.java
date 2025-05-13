package com.ua07.notifications.listener;

import com.ua07.notifications.command.NotificationCommand;
import com.ua07.notifications.command.NotificationInvoker;
import com.ua07.notifications.command.SendEmailNotificationCommand;
import com.ua07.notifications.command.SendInAppNotificationCommand;
import com.ua07.notifications.factory.NotificationFactory;
import com.ua07.notifications.models.Notification;
import com.ua07.notifications.models.NotificationType;
import com.ua07.notifications.repositories.PreferencesRepository;
import com.ua07.shared.rabbitmq.RabbitMQConstants;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import com.ua07.notifications.models.Preferences;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators.Not;
import org.springframework.stereotype.Service;

@Service
public class NotificationQueueListeners {
    NotificationFactory notificationFactory;
    PreferencesRepository preferencesRepository;
    NotificationInvoker notificationInvoker;
    SendEmailNotificationCommand sendEmailNotificationCommand;
    SendInAppNotificationCommand sendInAppNotificationCommand;

    public NotificationQueueListeners(NotificationFactory notificationFactory, PreferencesRepository preferencesRepository,
            NotificationInvoker notificationInvoker, SendEmailNotificationCommand sendEmailNotificationCommand,
            SendInAppNotificationCommand sendInAppNotificationCommand) {
        this.notificationFactory = notificationFactory;
        this.preferencesRepository = preferencesRepository;
        this.notificationInvoker = notificationInvoker;
        this.sendEmailNotificationCommand = sendEmailNotificationCommand;
        this.sendInAppNotificationCommand = sendInAppNotificationCommand;}
    @RabbitListener(queues = RabbitMQConstants.TRANSACTION_QUEUE)
    public void handleTransactionEvent(Map<String, Object> message) {

        UUID userId = UUID.fromString((String)message.get("userId"));
        NotificationType type = NotificationType.valueOf((String) message.get("type"));
        String msg = (String) message.get("message");
        Notification notification = NotificationFactory.createCustomerNotification(
               userId, msg, type
        );


        notificationInvoker.setCommand(sendInAppNotificationCommand);
        notificationInvoker.executeCommand(notification);

        Optional<Preferences> prefs = preferencesRepository.findByUserId(userId);
        if(prefs.isEmpty() || !prefs.get().getNotifyByMail()) {
            return;
        }

        if(prefs.get().getNotifyByMail()){
            notificationInvoker.setCommand(sendEmailNotificationCommand);
            notificationInvoker.executeCommand(notification);
        }



    }

    @RabbitListener(queues = RabbitMQConstants.MERCHANT_QUEUE)
    public void handleMerchantShortage(Map<String, Object> message) {
        UUID userId = UUID.fromString((String)message.get("userId"));
        String productIdInShortage = (String) message.get("productId");
        int currentCount = (int) message.get("remainingStock");

        Optional<Preferences> prefs = preferencesRepository.findByUserId(userId);

        if(prefs.isEmpty()) return;

        int threshold = prefs.get().getProductShortageThreshold();

        if(currentCount > threshold) {
            return;
        }

        Notification notification = NotificationFactory.createMerchantNotification(
                userId, productIdInShortage, currentCount, threshold
        );



        notificationInvoker.setCommand(sendInAppNotificationCommand);
        notificationInvoker.executeCommand(notification);


        if(prefs.get().getNotifyByMail()){
            notificationInvoker.setCommand(sendEmailNotificationCommand);
            notificationInvoker.executeCommand(notification);
        }

    }
}
