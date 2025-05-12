package com.ua07.users.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ua07.shared.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

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

    @JsonIgnore
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

    private User() {
    }

    private User(UUID id, String email, String phone, String password,
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
    public static class Builder {
        private final User user;

        public Builder() {
            user = new User();
        }

        public Builder withEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder withPhone(String phone) {
            user.setPhone(phone);
            return this;
        }

        public Builder withPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder withFullName(String fullName) {
            user.setFullName(fullName);
            return this;
        }

        public Builder withShippingAddress(String shippingAddress) {
            user.setShippingAddress(shippingAddress);
            return this;
        }

        public Builder withBillingAddress(String billingAddress) {
            user.setBillingAddress(billingAddress);
            return this;
        }

        public Builder withBusinessName(String businessName) {
            user.setBusinessName(businessName);
            return this;
        }

        public Builder withBusinessEmail(String businessEmail) {
            user.setBusinessEmail(businessEmail);
            return this;
        }

        public Builder withBusinessPhone(String businessPhone) {
            user.setBusinessPhone(businessPhone);
            return this;
        }

        public Builder withTaxId(String taxId) {
            user.setTaxId(taxId);
            return this;
        }

        public Builder withBusinessAddress(String businessAddress) {
            user.setBusinessAddress(businessAddress);
            return this;
        }

        public Builder withWebsiteUrl(String websiteUrl) {
            user.setWebsiteUrl(websiteUrl);
            return this;
        }

        public Builder withSupportContact(String supportContact) {
            user.setSupportContact(supportContact);
            return this;
        }

        public Builder withDepartment(String department) {
            user.setDepartment(department);
            return this;
        }

        public Builder withRole(Role role) {
            user.setRole(role);
            return this;
        }

        public User build() {
            return user;
        }
    }

}
