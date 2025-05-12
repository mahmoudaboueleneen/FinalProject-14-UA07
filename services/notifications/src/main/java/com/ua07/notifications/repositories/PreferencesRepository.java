package com.ua07.notifications.repositories;

import com.ua07.notifications.models.Preferences;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface PreferencesRepository extends MongoRepository<Preferences, String> {
    Optional<Preferences> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
}
