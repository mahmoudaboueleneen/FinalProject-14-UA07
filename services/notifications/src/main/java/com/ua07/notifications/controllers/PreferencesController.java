package com.ua07.notifications.controllers;

import com.ua07.notifications.models.Preferences;
import com.ua07.notifications.services.PreferencesService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("preferences")
public class PreferencesController {

    private final PreferencesService preferencesService;

    @Autowired
    public PreferencesController(PreferencesService preferencesService) {
        this.preferencesService = preferencesService;
    }

    @PostMapping
    public Preferences createOrUpdate(@RequestBody Preferences preferences) {
        return preferencesService.createOrUpdate(preferences);
    }

    @GetMapping
    public List<Preferences> getAll() {
        return preferencesService.getAll();
    }

    // TODO: Maybe get userId from the header? idk
    @GetMapping("/{userId}")
    public Optional<Preferences> getByUserId(@PathVariable UUID userId) {
        return preferencesService.getByUserId(userId);
    }

    // TODO: Maybe get userId from the header? idk
    @DeleteMapping("/{userId}")
    public void deleteByUserId(@PathVariable UUID userId) {
        preferencesService.deleteByUserId(userId);
    }

}
