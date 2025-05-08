package com.ua07.notifications.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;

    // Required Fields
    private Instant timestamp;
    private Boolean isRead;
    private UUID userId;



    // customer-specific
    @Nullable
    private String message;

    @Nullable
    private NotificationType type;



    //merchant-specific
    @Nullable
    private String productIdInShortage;

    @Nullable
    private Integer currentCount;

    @Nullable
    private Integer threshold;

    public Notification() {}

    public Notification(String id, Instant timestamp, Boolean isRead, UUID userId,
                        String message, NotificationType type,
                        String productIdInShortage, Integer currentCount, Integer threshold) {
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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public String getProductIdInShortage() { return productIdInShortage; }
    public void setProductIdInShortage(String productIdInShortage) { this.productIdInShortage = productIdInShortage; }

    public Integer getCurrentCount() { return currentCount; }
    public void setCurrentCount(Integer currentCount) { this.currentCount = currentCount; }

    public Integer getThreshold() { return threshold; }
    public void setThreshold(Integer threshold) { this.threshold = threshold; }
}
