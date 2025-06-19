package com.merrymeal.mealsonwheels_backend.controller;

import com.merrymeal.mealsonwheels_backend.dto.LoginDTO;
import com.merrymeal.mealsonwheels_backend.dto.RegisterDTO;
import com.merrymeal.mealsonwheels_backend.dto.UserDTO;
import com.merrymeal.mealsonwheels_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/admin")
    public ResponseEntity<UserDTO> registerAdmin(@Valid @RequestBody RegisterDTO registerDTO) {
        registerDTO.setRoleName("ROLE_ADMIN");
        // Removed: registerDTO.setUserType("ADMIN");
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @PostMapping("/register/member")
    public ResponseEntity<UserDTO> registerMember(@Valid @RequestBody RegisterDTO registerDTO) {
        registerDTO.setRoleName("ROLE_MEMBER");
        // Removed: registerDTO.setUserType("MEMBER");
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @PostMapping("/register/volunteer")
    public ResponseEntity<UserDTO> registerVolunteer(@Valid @RequestBody RegisterDTO registerDTO) {
        registerDTO.setRoleName("ROLE_VOLUNTEER");
        // Removed: registerDTO.setUserType("VOLUNTEER");
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @PostMapping("/register/rider")
    public ResponseEntity<UserDTO> registerRider(@Valid @RequestBody RegisterDTO registerDTO) {
        registerDTO.setRoleName("ROLE_RIDER");
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @PostMapping("/register/caregiver")
    public ResponseEntity<UserDTO> registerCaregiver(@Valid @RequestBody RegisterDTO registerDTO) {
        registerDTO.setRoleName("ROLE_CAREGIVER");
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @PostMapping("/register/partner")
    public ResponseEntity<UserDTO> registerPartner(@Valid @RequestBody RegisterDTO registerDTO) {
        registerDTO.setRoleName("ROLE_PARTNER");
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @PostMapping("/register/supporter")
    public ResponseEntity<UserDTO> registerSupporter(@Valid @RequestBody RegisterDTO registerDTO) {
        registerDTO.setRoleName("ROLE_SUPPORTER");
        // Removed: registerDTO.setUserType("SUPPORTER");
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @PostMapping("/register/donor")
    public ResponseEntity<UserDTO> registerDonor(@Valid @RequestBody RegisterDTO registerDTO) {
        registerDTO.setRoleName("ROLE_DONOR");
        // Removed: registerDTO.setUserType("DONOR");
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(authService.login(loginDTO));
        } catch (ResponseStatusException ex) {
            // If already thrown with a specific status, rethrow it
            throw ex;
        }
        // Removed the generic 'catch (Exception ex)' block here.
        // AccountNotApprovedException (a RuntimeException) will now
        // be caught by the GlobalExceptionHandler.
    }
}