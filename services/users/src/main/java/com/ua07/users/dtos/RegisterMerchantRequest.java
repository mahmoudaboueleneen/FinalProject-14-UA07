package com.ua07.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMerchantRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Business name is required")
    private String businessName;

    @NotBlank(message = "Business email is required")
    @Email(message = "Invalid business email format")
    private String businessEmail;

    @NotBlank(message = "Business phone is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid business phone number format")
    private String businessPhone;

    @NotBlank(message = "Tax ID is required")
    private String taxId;

    @NotBlank(message = "Business address is required")
    private String businessAddress;

    private String websiteUrl;

    private String supportContact;
}
