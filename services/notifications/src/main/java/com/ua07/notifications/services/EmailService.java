package com.ua07.notifications.services;

import com.ua07.notifications.models.Notification;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(Notification notification) {
        // Mock email logic (in real case: use JavaMailSender or external provider)
        System.out.println("Sending EMAIL to user " + notification.getUserId());
        System.out.println("Message: " + notification.getMessage());
    }
}
