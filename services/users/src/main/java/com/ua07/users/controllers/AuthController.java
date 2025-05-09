package com.ua07.users.controllers;

import com.ua07.shared.auth.AuthConstants;
import com.ua07.users.dtos.*;
import com.ua07.users.models.User;
import com.ua07.users.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
                    .header("Set-Cookie", AuthConstants.ACCESS_TOKEN_COOKIE + "=" + token + "; HttpOnly; Secure; SameSite=Strict")
                    .body("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody @Valid ChangePasswordRequest request,
            @RequestHeader(AuthConstants.USER_ID_HEADER) UUID userId
    ) {
        authService.changePassword(userId, request);
        return ResponseEntity.ok("Password changed successfully");
    }

}
