package com.ua07.notifications.models;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

@Setter
@Getter
@Document(collection = "notifications")
public class Notification {

    @Id private String id;

    // Required Fields
    private Instant timestamp;
    private Boolean isRead = false;
    private UUID userId;

    // customer-specific
    @Nullable private String message;

    @Nullable private NotificationType type;

    // merchant-specific
    @Nullable private String productIdInShortage;

    @Nullable private Integer currentCount;

    @Nullable private Integer threshold;

    public Notification() {}

    public Notification(
            String id,
            Instant timestamp,
            Boolean isRead,
            UUID userId,
            String message,
            NotificationType type,
            String productIdInShortage,
            Integer currentCount,
            Integer threshold) {
        this.id = id;
        this.timestamp = timestamp;
        this.isRead = isRead;
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.productIdInShortage = productIdInShortage;
        this.currentCount = currentCount;
        this.threshold = threshold;
    }

}
