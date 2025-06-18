package com.merrymeal.mealsonwheels.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")   
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard() {
        return ResponseEntity.ok("Welcome to Admin Dashboard");
    }

    @GetMapping("/test")
    public ResponseEntity<?> testAdminEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Admin endpoint accessible!");
        response.put("timestamp", new Date());
        response.put("userRole", "admin");
        return ResponseEntity.ok(response);
    }
}
