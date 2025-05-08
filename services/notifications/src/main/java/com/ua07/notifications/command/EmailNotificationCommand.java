package com.ua07.notifications.command;

import com.ua07.notifications.models.Notification;
import com.ua07.notifications.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationCommand implements NotificationCommand {

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(Notification notification) {
        emailService.sendEmail(notification);
    }
}
