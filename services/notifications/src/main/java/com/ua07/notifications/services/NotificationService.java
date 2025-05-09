package com.ua07.notifications.services;

import com.ua07.notifications.command.NotificationInvoker;
import com.ua07.notifications.command.SendEmailNotificationCommand;
import com.ua07.notifications.command.SendInAppNotificationCommand;
import com.ua07.notifications.models.Notification;
import com.ua07.notifications.repositories.NotificationRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired private NotificationRepository notificationRepository;

    @Autowired private NotificationInvoker invoker;

    public void sendInAppNotification(Notification notification) {
        SendInAppNotificationCommand sendInAppNotificationCommand =
                new SendInAppNotificationCommand();

        invoker.setCommand(sendInAppNotificationCommand);
        invoker.executeCommand(notification);
    }

    public void sendEmailNotification(Notification notification) {
        SendEmailNotificationCommand emailNotificationCommand = new SendEmailNotificationCommand();

        invoker.setCommand(emailNotificationCommand);
        invoker.executeCommand(notification);
    }

    public Notification createNotification(Notification notification) {
        notification.setTimestamp(Instant.now());
        notification.setIsRead(false);
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(String id) {
        return notificationRepository.findById(id);
    }

    public void deleteNotification(String id) {
        notificationRepository.deleteById(id);
    }

    public List<Notification> getUnreadNotifications(UUID userId, int page, int size) {
        return notificationRepository.findByUserIdAndIsReadFalse(
                userId); // add pagination if needed
    }

    public Notification markAsRead(String id) {
        Optional<Notification> notif = notificationRepository.findById(id);
        if (notif.isPresent()) {
            Notification n = notif.get();
            n.setIsRead(true);
            return notificationRepository.save(n);
        }
        return null;
    }

    public void markAllAsRead(UUID userId) {
        List<Notification> notifications =
                notificationRepository.findByUserIdAndIsReadFalse(userId);
        notifications.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(notifications);
    }
}
