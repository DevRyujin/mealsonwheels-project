package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.roleDTOs.SupporterProfileDTO;
import com.merrymeal.mealsonwheels.service.roleService.SupporterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supporter")
@RequiredArgsConstructor
public class SupporterController {

    private final SupporterService supporterService;

    @GetMapping("/profile")
    public ResponseEntity<SupporterProfileDTO> getProfile() {
        return ResponseEntity.ok(supporterService.getSupporterProfile());
    }
}
