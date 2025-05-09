package com.ua07.notifications.command;

import com.ua07.notifications.models.Notification;
import com.ua07.notifications.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendInAppNotificationCommand implements NotificationCommand {

    @Autowired private NotificationRepository notificationRepository;

    @Override
    public void execute(Notification notification) {
        notificationRepository.save(notification); // In-app means persisting to DB
    }
}
