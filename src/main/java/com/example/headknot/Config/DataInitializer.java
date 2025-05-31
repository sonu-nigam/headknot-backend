package com.example.headknot.Config;

import com.example.headknot.User.UserEntity;
import com.example.headknot.User.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        UserEntity adminUserExists = userRepository.findByEmail("admin@test.com");
        if (adminUserExists != null) return;

        String hashedPassword = passwordEncoder.encode("password");

        UserEntity user = new UserEntity();
        user.setFullName("Jane Doe");
        user.setEmail("admin@test.com");
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}