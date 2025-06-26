package com.merrymeal.mealsonwheels.service;

import com.merrymeal.mealsonwheels.dto.*;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.security.CustomUserDetails;
import com.merrymeal.mealsonwheels.security.JwtTokenProvider;
import org.springframework.security.authentication.*;
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

        // ✅ Approval check
        if (!user.isApproved()) {
            throw new RuntimeException("Account is not approved by admin yet.");
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtTokenProvider.generateToken(userDetails, user.getId());

        // ✅ Now build and return AuthResponse
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUserType(user.getRole().name());
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setMessage("Login successful");

        return response;
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        // Delegate to RegistrationService if you want to reuse it
        throw new UnsupportedOperationException("Use RegistrationService for registration.");
    }
}
