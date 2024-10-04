package com.example.library_management.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String mobile;
    private String password;
    private String role;
}
