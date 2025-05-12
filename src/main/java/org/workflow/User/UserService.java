package org.workflow.User;

import org.workflow.Auth.LoginRequest;
import org.workflow.Auth.RegisterRequest;

import java.util.UUID;

public interface UserService {
    UserEntity registerUser(RegisterRequest registerRequest);

    UserEntity authenticateUser(LoginRequest LoginRequest);

    UserDTO getUserById(UUID userId);
}
