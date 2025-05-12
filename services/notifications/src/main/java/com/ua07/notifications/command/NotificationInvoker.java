package com.ua07.notifications.command;

import com.ua07.notifications.models.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationInvoker {

    private NotificationCommand command;

    public void setCommand(NotificationCommand command) {
        this.command = command;
    }

    public void executeCommand(Notification notification) {
        if (command != null) {
            command.execute(notification);
        }
    }
}
