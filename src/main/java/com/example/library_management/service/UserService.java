package com.example.library_management.service;

import com.example.library_management.dto.UserRegistrationDTO;
import com.example.library_management.model.User;

public interface UserService {
    void registerUser(UserRegistrationDTO userRegistrationDTO);
}
