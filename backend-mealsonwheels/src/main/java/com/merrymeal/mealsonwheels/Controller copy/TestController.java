package com.merrymeal.mealsonwheels_backend.controller; // Or appropriate package

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController { // Renamed from HomeController to avoid confusion if you already have one

   
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "MelsOnWels Backend is running!");
        return response;
    }

    @GetMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", "Hello from MelsOnWels Backend!");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}