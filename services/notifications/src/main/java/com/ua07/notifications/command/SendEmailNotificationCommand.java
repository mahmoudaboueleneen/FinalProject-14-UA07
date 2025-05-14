package com.ua07.notifications.command;

import com.ua07.notifications.client.UserClient;
import com.ua07.notifications.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ua07.notifications.models.Notification;
import org.springframework.web.server.ResponseStatusException;

@Component
public class SendEmailNotificationCommand implements NotificationCommand {

    @Autowired
    private JavaMailSender mailSender;

    private UserClient userClient;

    @Value("${spring.email.username}")
    private String from;

    @Override
    public void execute(Notification notification) {
        User user = userClient.getUserById(notification.getUserId()).getBody();

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + notification.getUserId());
        }

        if (notification.getProductIdInShortage() != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(user.getEmail());
            message.setSubject("Product with ID: " + notification.getProductIdInShortage()
                    + " has fallen under the stock threshold of " + notification.getThreshold()
                    + " with current count of " + notification.getCurrentCount());
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject(notification.getMessage());
        message.setText(notification.getMessage());
        mailSender.send(message);
    }

}
