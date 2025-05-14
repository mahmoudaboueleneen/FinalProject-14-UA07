package com.ua07.notifications.command;

import com.ua07.notifications.models.Notification;
import com.ua07.notifications.repositories.NotificationRepository;

public class SendInAppNotificationCommand implements NotificationCommand {

    private final NotificationRepository notificationRepository;

    public SendInAppNotificationCommand(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void execute(Notification notification) {
        notificationRepository.save(notification); // In-app means persisting to DB
    }
}
