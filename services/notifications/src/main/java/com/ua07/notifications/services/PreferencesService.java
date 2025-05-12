package com.ua07.notifications.services;

import com.ua07.notifications.models.Preferences;
import com.ua07.notifications.repositories.PreferencesRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferencesService {

    private final PreferencesRepository preferencesRepository;

    @Autowired
    public PreferencesService(PreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    public Preferences createOrUpdate(Preferences preferences) {
        Optional<Preferences> existingPreferences = preferencesRepository.findByUserId(preferences.getUserId());
        if (existingPreferences.isPresent()) {
            // Handle update scenario
            Preferences existing = existingPreferences.get();
            existing.setNotifyByMail(preferences.getNotifyByMail());
            existing.setProductShortageThreshold(preferences.getProductShortageThreshold());
            return preferencesRepository.save(existing);
        }
        return preferencesRepository.save(preferences); // Create new entry if not present
    }

    public List<Preferences> getAll() {
        return preferencesRepository.findAll();
    }

    public Optional<Preferences> getByUserId(UUID userId) {
        return preferencesRepository.findByUserId(userId);
    }

    public void deleteByUserId(UUID userId) {
        preferencesRepository.deleteByUserId(userId);
    }
}
