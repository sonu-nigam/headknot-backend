package com.example.headknot.User;

import com.example.headknot.Auth.LoginRequest;
import com.example.headknot.Auth.RegisterRequest;

import java.util.UUID;

public interface UserService {
    UserEntity registerUser(RegisterRequest registerRequest);

    UserEntity authenticateUser(LoginRequest LoginRequest);

    UserDTO getUserById(UUID userId);
}
