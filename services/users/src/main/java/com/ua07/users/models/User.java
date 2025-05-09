package com.ua07.users.models;

import com.ua07.shared.enums.Role;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Builder(setterPrefix = "with")
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
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

    public User() {
    }

    public User(UUID id, String email, String phone, String password,
                Role role, Instant createdAt, String fullName, String shippingAddress,
                String billingAddress, String businessName, String businessEmail,
                String businessPhone, String taxId, String businessAddress,
                String websiteUrl, String supportContact, String department) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.fullName = fullName;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.businessName = businessName;
        this.businessEmail = businessEmail;
        this.businessPhone = businessPhone;
        this.taxId = taxId;
        this.businessAddress = businessAddress;
        this.websiteUrl = websiteUrl;
        this.supportContact = supportContact;
        this.department = department;
    }

    public User(
            String email,
            String phone,
            String password,
            Role role,
            Instant createdAt,
            String fullName,
            String shippingAddress,
            String billingAddress,
            String businessName,
            String businessEmail,
            String businessPhone,
            String taxId,
            String businessAddress,
            String websiteUrl,
            String supportContact,
            String department) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.fullName = fullName;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.businessName = businessName;
        this.businessEmail = businessEmail;
        this.businessPhone = businessPhone;
        this.taxId = taxId;
        this.businessAddress = businessAddress;
        this.websiteUrl = websiteUrl;
        this.supportContact = supportContact;
        this.department = department;
    }

}
