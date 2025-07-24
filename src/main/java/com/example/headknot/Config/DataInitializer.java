package com.example.headknot.Config;

import com.example.headknot.User.UserEntity;
import com.example.headknot.User.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        UserEntity adminUserExists = userRepository.findByEmail("admin@test.com");
        if (adminUserExists != null)
            return;

        String hashedPassword = passwordEncoder.encode("password");

        UserEntity user = new UserEntity(
                null, // id will be generated
                "Jane", // firstName
                "Doe", // lastName
                hashedPassword, // password
                null, // roles will be set later
                "admin@test.com" // email
        );
        userRepository.save(user);
    }
}
