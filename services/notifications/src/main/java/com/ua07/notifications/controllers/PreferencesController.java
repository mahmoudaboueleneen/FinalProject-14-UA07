package com.ua07.notifications.controllers;

import com.ua07.notifications.models.Preferences;
import com.ua07.notifications.services.PreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("preferences")
public class PreferencesController {

    @Autowired
    private PreferencesService service;

    @PostMapping
    public Preferences createOrUpdate(@RequestBody Preferences preferences) {
        return service.createOrUpdate(preferences);
    }

    @GetMapping
    public List<Preferences> getAll() {
        return service.getAll();
    }

    @GetMapping("/{userId}")
    public Optional<Preferences> getById(@PathVariable UUID userId) {
        return service.getByUserId(userId);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable UUID userId) {
        service.delete(userId);
    }
}
