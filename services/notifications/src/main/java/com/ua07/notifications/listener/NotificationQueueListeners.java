package com.ua07.notifications.listener;

import com.ua07.notifications.client.UserClient;
import com.ua07.notifications.command.NotificationInvoker;
import com.ua07.notifications.command.SendEmailNotificationCommand;
import com.ua07.notifications.command.SendInAppNotificationCommand;
import com.ua07.notifications.factory.NotificationFactory;
import com.ua07.notifications.models.Notification;
import com.ua07.notifications.models.NotificationType;
import com.ua07.notifications.repositories.NotificationRepository;
import com.ua07.notifications.repositories.PreferencesRepository;
import com.ua07.shared.rabbitmq.RabbitMQConstants;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.ua07.notifications.models.Preferences;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationQueueListeners {

    private final PreferencesRepository preferencesRepository;
    private final NotificationInvoker notificationInvoker;
    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private final UserClient userClient;

    public NotificationQueueListeners(PreferencesRepository preferencesRepository, NotificationInvoker notificationInvoker, NotificationRepository notificationRepository, JavaMailSender mailSender, UserClient userClient) {
        this.preferencesRepository = preferencesRepository;
        this.notificationInvoker = notificationInvoker;
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
        this.userClient = userClient;
    }

    @RabbitListener(queues = RabbitMQConstants.TRANSACTION_QUEUE)
    public void handleTransactionEvent(Map<String, Object> message) {

        UUID userId = UUID.fromString((String) message.get("userId"));
        NotificationType type = NotificationType.valueOf((String) message.get("type"));
        String msg = (String) message.get("message");
        Notification notification = NotificationFactory.createCustomerNotification(
                userId, msg, type
        );

        SendInAppNotificationCommand sendInAppNotificationCommand = new SendInAppNotificationCommand(notificationRepository);
        notificationInvoker.setCommand(sendInAppNotificationCommand);
        notificationInvoker.executeCommand(notification);

        Optional<Preferences> preferences = preferencesRepository.findByUserId(userId);
        if (preferences.isEmpty() || !preferences.get().getNotifyByMail()) {
            return;
        }

        if (preferences.get().getNotifyByMail()) {
            SendEmailNotificationCommand sendEmailNotificationCommand = new SendEmailNotificationCommand(mailSender, userClient);
            notificationInvoker.setCommand(sendEmailNotificationCommand);
            notificationInvoker.executeCommand(notification);
        }

    }

    @RabbitListener(queues = RabbitMQConstants.MERCHANT_QUEUE)
    public void handleMerchantShortage(Map<String, Object> message) {
        UUID userId = UUID.fromString((String) message.get("userId"));
        String productIdInShortage = (String) message.get("productId");
        int currentCount = (int) message.get("remainingStock");

        Optional<Preferences> preferences = preferencesRepository.findByUserId(userId);

        if (preferences.isEmpty()) return;

        int threshold = preferences.get().getProductShortageThreshold() == null
                ? 0 : preferences.get().getProductShortageThreshold();

        if (currentCount > threshold) {
            return;
        }

        Notification notification = NotificationFactory.createMerchantNotification(
                userId, productIdInShortage, currentCount, threshold
        );

        SendInAppNotificationCommand sendInAppNotificationCommand = new SendInAppNotificationCommand(notificationRepository);
        notificationInvoker.setCommand(sendInAppNotificationCommand);
        notificationInvoker.executeCommand(notification);

        if (preferences.get().getNotifyByMail()) {
            SendEmailNotificationCommand sendEmailNotificationCommand = new SendEmailNotificationCommand(mailSender, userClient);
            notificationInvoker.setCommand(sendEmailNotificationCommand);
            notificationInvoker.executeCommand(notification);
        }

    }
}
