package com.ua07.notifications.command;

import com.ua07.notifications.models.Notification;

public interface NotificationCommand {
    void execute(Notification notification);
}
