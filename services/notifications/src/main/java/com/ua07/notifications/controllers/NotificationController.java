package com.ua07.notifications.controllers;

import com.ua07.notifications.models.Notification;
import com.ua07.notifications.services.NotificationService;
import java.util.List;
import java.util.UUID;

import com.ua07.shared.auth.AuthConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable String id) {
        return notificationService.getNotificationById(id);
    }

    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @PutMapping("/{id}")
    public Notification updateNotification(@PathVariable String id, @RequestBody Notification notification) {
        return notificationService.updateNotification(id, notification);
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable String id) {
        notificationService.deleteNotification(id);
    }

    @GetMapping("/unread")
    public List<Notification> getUnread(
            @RequestHeader(AuthConstants.USER_ID_HEADER) UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return notificationService.getUnreadNotifications(userId, page, size);
    }

    @PostMapping("/mark-read/{id}")
    public Notification markOneAsRead(@PathVariable String id) {
        return notificationService.markAsRead(id);
    }

    // Note: This is problematic for cache eviction. Leave it out for now.
//    @PostMapping("/mark-all-read")
//    public void markAllAsRead(@RequestHeader(AuthConstants.USER_ID_HEADER) UUID userId) {
//        notificationService.markAllAsRead(userId);
//    }

    @PostMapping("/send/in-app")
    public void sendInApp(@RequestBody Notification notification) {
        notificationService.sendInAppNotification(notification);
    }

    @PostMapping("/send/email")
    public void sendEmail(@RequestBody Notification notification) {
        notificationService.sendEmailNotification(notification);
    }

}
