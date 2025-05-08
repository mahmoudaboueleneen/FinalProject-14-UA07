package com.ua07.notifications.repositories;

import com.ua07.notifications.models.Preferences;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface PreferencesRepository extends MongoRepository<Preferences, UUID> {
}
