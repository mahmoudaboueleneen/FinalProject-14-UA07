package com.ua07.notifications.repositories;

import com.ua07.notifications.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUserIdAndIsReadFalse(UUID userId);
}
