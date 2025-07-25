package com.merrymeal.mealsonwheels.service;

import com.merrymeal.mealsonwheels.dto.*;
import com.merrymeal.mealsonwheels.exception.AccountNotApprovedException;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.dto.UserDTO;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.security.CustomUserDetails;
import com.merrymeal.mealsonwheels.security.JwtTokenProvider;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
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
        // New
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (DisabledException e) {
            throw new AccountNotApprovedException("Your account has not been approved yet by the admin.");
        }

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .age(user.getAge())
                .address(user.getAddress())
                .latitude(user.getLatitude())
                .longitude(user.getLongitude())
                .role(user.getRole())
                .approved(user.isApproved())
                .dietaryRestrictions(user.getDietaryRestrictions()) // if not null
                .build();


        // ✅ Approval check
        if (!user.isApproved()) {
            throw new AccountNotApprovedException("Your account has not been approved yet by the admin.");
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtTokenProvider.generateToken(userDetails, user.getId());

        // Create AuthResponse manually (no builder)
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUserType(user.getRole().name());
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setMessage("Login successful");
        response.setName(user.getName());
        response.setUser(userDTO); // ✅ include full user details here

        System.out.println("🔍 AuthResponse contents:");
        System.out.println("Token: " + response.getToken());
        System.out.println("User ID: " + response.getUserId());
        System.out.println("User: " + response.getUser());  // this is most important


        return response;
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        // Delegate to RegistrationService if you want to reuse it
        throw new UnsupportedOperationException("Use RegistrationService for registration.");
    }


}
