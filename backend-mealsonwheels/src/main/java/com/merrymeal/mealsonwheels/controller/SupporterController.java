package com.merrymeal.mealsonwheels_backend.controller;

import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.SupporterDTO;
import com.merrymeal.mealsonwheels_backend.service.roleService.SupporterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supporter")
@RequiredArgsConstructor
public class SupporterController {

    private final SupporterService supporterService;

    @GetMapping("/profile")
    public ResponseEntity<SupporterDTO> getProfile() {
        return ResponseEntity.ok(supporterService.getSupporterProfile());
    }
}
