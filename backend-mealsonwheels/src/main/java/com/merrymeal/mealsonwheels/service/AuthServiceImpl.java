package com.merrymeal.mealsonwheels.service;

import com.merrymeal.mealsonwheels.dto.*;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.security.CustomUserDetails;
import com.merrymeal.mealsonwheels.security.JwtTokenProvider;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isApproved()) {
            throw new RuntimeException("Account is not approved by admin yet.");
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtTokenProvider.generateToken(userDetails, user.getId());

        return new AuthResponse(token, user.getRole().name());
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        // Delegate to RegistrationService if you want to reuse it
        throw new UnsupportedOperationException("Use RegistrationService for registration.");
    }
}
