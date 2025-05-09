package com.ua07.users.controllers;

import com.ua07.shared.auth.AuthConstants;
import com.ua07.users.models.User;
import com.ua07.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/test")
    public String test() {
        return "Hello from User Service";
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Get current user
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(@RequestHeader(value = AuthConstants.USER_ID_HEADER, required = true) UUID userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build(); // or throw a custom exception
        }
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
