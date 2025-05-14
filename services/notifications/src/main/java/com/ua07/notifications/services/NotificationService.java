package com.ua07.notifications.services;

import com.ua07.notifications.client.UserClient;
import com.ua07.notifications.command.NotificationInvoker;
import com.ua07.notifications.command.SendEmailNotificationCommand;
import com.ua07.notifications.command.SendInAppNotificationCommand;
import com.ua07.notifications.models.Notification;
import com.ua07.notifications.repositories.NotificationRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationInvoker invoker;
    private final JavaMailSender mailSender;
    private final UserClient userClient;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, NotificationInvoker invoker, JavaMailSender mailSender, UserClient userClient) {
        this.notificationRepository = notificationRepository;
        this.invoker = invoker;
        this.mailSender = mailSender;
        this.userClient = userClient;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(String id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found with id " + id));
    }

    public Notification createNotification(Notification notification) {
        notification.setTimestamp(Instant.now());
        notification.setIsRead(false);
        return notificationRepository.save(notification);
    }

    public void deleteNotification(String id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found with id " + id);
        }
        notificationRepository.deleteById(id);
    }

    public void sendInAppNotification(Notification notification) {
        SendInAppNotificationCommand sendInAppNotificationCommand =
                new SendInAppNotificationCommand(notificationRepository);

        invoker.setCommand(sendInAppNotificationCommand);
        invoker.executeCommand(notification);
    }

    public void sendEmailNotification(Notification notification) {
        SendEmailNotificationCommand emailNotificationCommand = new SendEmailNotificationCommand(mailSender, userClient);

        invoker.setCommand(emailNotificationCommand);
        invoker.executeCommand(notification);
    }

    public List<Notification> getUnreadNotifications(UUID userId, int page, int size) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId); // add pagination if needed
    }

    public Notification markAsRead(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found with id " + id));

        notification.setIsRead(true);
        return notificationRepository.save(notification);
    }

    public void markAllAsRead(UUID userId) {
        List<Notification> notifications =
                notificationRepository.findByUserIdAndIsReadFalse(userId);
        notifications.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(notifications);
    }
}
