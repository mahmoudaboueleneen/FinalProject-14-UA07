package com.ua07.notifications.dtos;

import com.ua07.shared.enums.Role;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;
    private String email;
    private String phone;
//    private String password;
    private Role role;
    private Instant createdAt;

    // Common
    private String fullName;

    // CUSTOMER
    private String shippingAddress;
    private String billingAddress;

    // MERCHANT
    private String businessName;
    private String businessEmail;
    private String businessPhone;
    private String taxId;
    private String businessAddress;
    private String websiteUrl;
    private String supportContact;

    // ADMIN
    private String department;
}

