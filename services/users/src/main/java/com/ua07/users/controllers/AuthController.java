package com.ua07.users.controllers;

import com.ua07.users.dtos.LoginRequest;
import com.ua07.users.dtos.RegisterAdminRequest;
import com.ua07.users.dtos.RegisterCustomerRequest;
import com.ua07.users.dtos.RegisterMerchantRequest;
import com.ua07.users.models.User;
import com.ua07.users.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.login(loginRequest);
            return ResponseEntity.ok()
                    .header("Set-Cookie", "token=" + token + "; HttpOnly; Secure; SameSite=Strict")
                    .body("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<User> registerAdmin(@RequestBody RegisterAdminRequest request) {
        User user = authService.registerAdmin(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register/merchant")
    public ResponseEntity<User> registerMerchant(@RequestBody RegisterMerchantRequest request) {
        User user = authService.registerMerchant(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register/customer")
    public ResponseEntity<User> registerCustomer(@RequestBody RegisterCustomerRequest request) {
        User user = authService.registerCustomer(request);
        return ResponseEntity.ok(user);
    }

}
