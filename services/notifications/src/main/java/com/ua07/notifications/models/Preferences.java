package com.ua07.notifications.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Document(collection = "preferences")
public class Preferences {

    @Id
    private UUID userId;

    private Boolean notifyByMail;

    @Nullable
    private Integer productShortageThreshold;

    // Constructors
    public Preferences() {}

    public Preferences(UUID userId, Boolean notifyByMail, Integer productShortageThreshold) {
        this.userId = userId;
        this.notifyByMail = notifyByMail;
        this.productShortageThreshold = productShortageThreshold;
    }

    // Getters and Setters

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public Boolean getNotifyByMail() { return notifyByMail; }
    public void setNotifyByMail(Boolean notifyByMail) { this.notifyByMail = notifyByMail; }

    public Integer getProductShortageThreshold() { return productShortageThreshold; }
    public void setProductShortageThreshold(Integer productShortageThreshold) {
        this.productShortageThreshold = productShortageThreshold;
    }
}
