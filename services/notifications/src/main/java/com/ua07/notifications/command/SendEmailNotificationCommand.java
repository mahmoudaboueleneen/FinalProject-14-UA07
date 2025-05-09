package com.ua07.notifications.command;

import com.ua07.notifications.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class SendEmailNotificationCommand implements NotificationCommand {


    @Override
    public void execute(Notification notification) {
        
    }
}
