package com.example.headknot.Auth;

import com.example.headknot.User.UserDTO;
import com.example.headknot.User.UserEntity;
import com.example.headknot.User.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest loginRequest) {
        UserEntity user = userService.authenticateUser(loginRequest);
        String token = jwtService.generateToken(user.getEmail());
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        LoginResponseDTO response = new LoginResponseDTO(token, userDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest userRequest) {
        UserEntity user = userService.registerUser(userRequest);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }
}
