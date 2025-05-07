package com.ua07.users.models;

import com.ua07.shared.enums.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

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

    public User(String email, String phone, String password, Role role, Instant createdAt, String fullName, String shippingAddress, String billingAddress,
                String businessName, String businessEmail, String businessPhone, String taxId,
                String businessAddress, String websiteUrl, String supportContact, String department) {
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getSupportContact() {
        return supportContact;
    }

    public void setSupportContact(String supportContact) {
        this.supportContact = supportContact;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
