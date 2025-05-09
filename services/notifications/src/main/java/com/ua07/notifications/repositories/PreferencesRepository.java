package com.ua07.notifications.repositories;

import com.ua07.notifications.models.Preferences;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PreferencesRepository extends MongoRepository<Preferences, UUID> {}
