package com.ua07.users.services;

import com.ua07.shared.enums.Role;
import com.ua07.users.dtos.RegisterAdminRequest;
import com.ua07.users.dtos.RegisterCustomerRequest;
import com.ua07.users.dtos.RegisterMerchantRequest;
import com.ua07.users.models.User;
import com.ua07.users.repositories.UserRepository;
import com.ua07.shared.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User registerAdmin(RegisterAdminRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .withEmail(request.getEmail())
                .withPhone(request.getPhone())
                .withPassword(encodedPassword)
                .withFullName(request.getFullName())
                .withDepartment(request.getDepartment())
                .withRole(Role.ADMIN)
                .build();
        return userRepository.save(user);
    }

    public User registerMerchant(RegisterMerchantRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .withEmail(request.getEmail())
                .withPhone(request.getPhone())
                .withPassword(encodedPassword)
                .withFullName(request.getFullName())
                .withBusinessName(request.getBusinessName())
                .withBusinessEmail(request.getBusinessEmail())
                .withBusinessPhone(request.getBusinessPhone())
                .withTaxId(request.getTaxId())
                .withBusinessAddress(request.getBusinessAddress())
                .withWebsiteUrl(request.getWebsiteUrl())
                .withSupportContact(request.getSupportContact())
                .withRole(Role.MERCHANT)
                .build();
        return userRepository.save(user);
    }

    public User registerCustomer(RegisterCustomerRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .withEmail(request.getEmail())
                .withPhone(request.getPhone())
                .withPassword(encodedPassword)
                .withFullName(request.getFullName())
                .withShippingAddress(request.getShippingAddress())
                .withBillingAddress(request.getBillingAddress())
                .withRole(Role.CUSTOMER)
                .build();
        return userRepository.save(user);
    }
    public String login(String identifier, String password) {
        User user = getUserByIdentifier(identifier);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate the JWT token for the authenticated user
        String token = jwtService.generateToken(user);
        return token;
    }

    private User getUserByIdentifier(String identifier) {
        // Check if the identifier is an email or phone and retrieve the user
        if (identifier.contains("@")) {
            return userRepository.findByEmail(identifier).orElse(null);
        } else {
            return userRepository.findByPhone(identifier).orElse(null);
        }
    }
}
