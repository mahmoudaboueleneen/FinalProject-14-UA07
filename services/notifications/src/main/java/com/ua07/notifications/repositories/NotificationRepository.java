package com.ua07.notifications.repositories;

import com.ua07.notifications.models.Notification;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUserIdAndIsReadFalse(UUID userId);
}
