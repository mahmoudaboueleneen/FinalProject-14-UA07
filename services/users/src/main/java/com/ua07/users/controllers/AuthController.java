package com.ua07.users.controllers;

import com.ua07.users.dtos.RegisterAdminRequest;
import com.ua07.users.dtos.RegisterCustomerRequest;
import com.ua07.users.dtos.RegisterMerchantRequest;
import com.ua07.users.models.User;
import com.ua07.users.services.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Authenticator authenticator;

    @Autowired
    public AuthController(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    // Endpoint to handle user login via either email or phone
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String identifier, @RequestParam String password) {
        try {
            // Delegate authentication to the Authenticator
            String token = authenticator.login(identifier, password);
            // Set the JWT token in HttpOnly cookie and return the response
            return ResponseEntity.ok()
                    .header("Set-Cookie", "token=" + token + "; HttpOnly; Secure; SameSite=Strict")
                    .body("Login successful");
        } catch (Exception e) {
            // Handle login failure
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<User> registerAdmin(@RequestBody RegisterAdminRequest request) {
        User user = AuthService.registerAdmin(request);
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
