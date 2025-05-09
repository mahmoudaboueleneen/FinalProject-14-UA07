package com.ua07.notifications.controllers;

import com.ua07.notifications.models.Notification;
import com.ua07.notifications.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return service.createNotification(notification);
    }

    @GetMapping
    public List<Notification> getAll() {
        return service.getAllNotifications();
    }

    @GetMapping("/{id}")
    public Optional<Notification> getById(@PathVariable String id) {
        return service.getNotificationById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteNotification(id);
    }

    @GetMapping("/unread/")
    public List<Notification> getUnread(@RequestParam UUID userId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return service.getUnreadNotifications(userId, page, size);
    }

    @PostMapping("/mark-read/{id}")
    public Notification markOneAsRead(@PathVariable String id) {
        return service.markAsRead(id);
    }

    @PostMapping("/mark-all-read/")
    public void markAllAsRead(@RequestParam UUID userId) {
        service.markAllAsRead(userId);
    }

    @PostMapping("/send/in-app")
    public void sendInApp(@RequestBody Notification notification) {
        service.sendInAppNotification(notification);
    }

    @PostMapping("/send/email")
    public void sendEmail(@RequestBody Notification notification) {
        service.sendEmailNotification(notification);
    }

}
