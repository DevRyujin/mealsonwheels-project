package com.merrymeal.mealsonwheels.service;

import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            return jwtService.generateToken(email); // You can pass the user's email as the subject
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
