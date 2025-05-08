package com.ua07.users.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCustomerRequest {
    private String email;
    private String phone;
    private String password;
    private String fullName;

    private String shippingAddress;
    private String billingAddress;
}
