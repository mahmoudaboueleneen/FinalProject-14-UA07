package com.ua07.users.dtos;


import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMerchantRequest {
    private String email;
    private String phone;
    private String password;
    private String fullName;

    private String businessName;
    private String businessEmail;
    private String businessPhone;
    private String taxId;
    private String businessAddress;
    private String websiteUrl;
    private String supportContact;
}
