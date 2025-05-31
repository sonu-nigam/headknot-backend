package com.example.headknot.User;

import com.example.headknot.Auth.LoginRequest;
import com.example.headknot.Auth.RegisterRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity registerUser(RegisterRequest payload) {
        if (userRepository.findByEmail(payload.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }

        String hashedPassword = passwordEncoder.encode(payload.getPassword());

        UserEntity user = modelMapper.map(payload, UserEntity.class);
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public UserEntity authenticateUser(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail());
        boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (user == null || !passwordMatches) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

    public UserDTO getUserById(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return modelMapper.map(user, UserDTO.class);
    }
}

