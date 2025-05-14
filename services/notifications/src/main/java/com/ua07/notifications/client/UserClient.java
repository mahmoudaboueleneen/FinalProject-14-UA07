package com.ua07.notifications.client;

import com.ua07.notifications.dtos.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "users-service", url = "${users.service.url}")
public interface UserClient {
    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable UUID id);
}
