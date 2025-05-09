package com.ua07.users.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAdminRequest {
    private String email;
    private String phone;
    private String password;
    private String fullName;
    private String department;
}
