package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.AuthResponse;
import com.merrymeal.mealsonwheels.dto.LoginRequest;
import com.merrymeal.mealsonwheels.dto.LoginResponse;
import com.merrymeal.mealsonwheels.dto.RegisterRequest;
import com.merrymeal.mealsonwheels.service.AuthService;
import com.merrymeal.mealsonwheels.service.RegistrationService;

import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Date;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private RegistrationService registrationService;

    private AuthService authService;

    public AuthController(RegistrationService registrationService, AuthService authService) {
        this.registrationService = registrationService;
        this.authService = authService;
    }

    // This should be accessible without authentication
    @GetMapping("/test")
    public ResponseEntity<?> testConnection() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Backend connection successful!");
        response.put("timestamp", new Date());
        response.put("status", "OK");
        response.put("cors", "enabled");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        System.out.println("Incoming register request: " + registerRequest); // ✅ DEBUG

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
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid email or password: " + e.getMessage());
        }
    }

    // For logout logic
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        // 1. Get Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid or missing token");
        }

        // 2. Extract token
        String token = authHeader.substring(7);

        // 3. Optionally blacklist the token or invalidate it
        // e.g., tokenBlacklistService.add(token);
        System.out.println("Logging out token: " + token); // optional log

        // 4. Respond
        return ResponseEntity.ok("Logout successful");
    }

}
