package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.LoginRequest;
import com.merrymeal.mealsonwheels.dto.LoginResponse;
import com.merrymeal.mealsonwheels.dto.RegisterRequest;
import com.merrymeal.mealsonwheels.security.JwtService;
import com.merrymeal.mealsonwheels.service.AuthService;
import com.merrymeal.mealsonwheels.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private RegistrationService registrationService;


    private AuthService authService;

    public AuthController(RegistrationService registrationService, AuthService authService) {
        this.registrationService = registrationService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            registrationService.register(registerRequest);
            return ResponseEntity.ok("Registration successful!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong. Please try again.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Attempt login, get token if successful
            String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (RuntimeException e) {
            // Return 401 Unauthorized with a helpful message
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}
