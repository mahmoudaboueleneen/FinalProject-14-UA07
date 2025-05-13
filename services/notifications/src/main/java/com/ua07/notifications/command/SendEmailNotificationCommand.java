package com.ua07.notifications.command;

import org.springframework.stereotype.Component;

import com.ua07.notifications.models.Notification;

@Component  
public class SendEmailNotificationCommand implements NotificationCommand {

    @Override
    public void execute(Notification notification) {}
}
