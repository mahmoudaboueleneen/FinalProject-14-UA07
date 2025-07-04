package com.ua07.notifications.command;

import com.ua07.notifications.client.UserClient;
import com.ua07.notifications.dtos.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.ua07.notifications.models.Notification;
import org.springframework.web.server.ResponseStatusException;

public class SendEmailNotificationCommand implements NotificationCommand {

    private String from;

    private final JavaMailSender mailSender;
    private final UserClient userClient;

    public SendEmailNotificationCommand(JavaMailSender mailSender, UserClient userClient) {
        this.mailSender = mailSender;
        this.userClient = userClient;

        this.from = System.getenv("SPRING_MAIL_USERNAME");
    }

    @Override
    public void execute(Notification notification) {
        User user = userClient.getUserById(notification.getUserId()).getBody();

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + notification.getUserId());
        }

        // Merchant notification
        if (notification.getProductIdInShortage() != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(user.getEmail());
            message.setSubject("Product Shortage Notification");
            message.setText("Product with ID: " + notification.getProductIdInShortage()
                    + " has fallen under the stock threshold of " + notification.getThreshold()
                    + " with current count of " + notification.getCurrentCount());
            mailSender.send(message);
            return;
        }

        // Customer notification
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject(notification.getMessage());
        message.setText(notification.getMessage());
        mailSender.send(message);
    }

}
