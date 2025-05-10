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

    @Autowired private PreferencesRepository repo;

    public Preferences createOrUpdate(Preferences prefs) {
        return repo.save(prefs);
    }

    public List<Preferences> getAll() {
        return repo.findAll();
    }

    public Optional<Preferences> getByUserId(UUID userId) {
        return repo.findById(userId);
    }

    public void delete(UUID userId) {
        repo.deleteById(userId);
    }
}
