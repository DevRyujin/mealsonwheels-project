package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.roleDTOs.DonorProfileDTO;
import com.merrymeal.mealsonwheels.service.roleService.DonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/donor")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;

    @GetMapping("/profile")
    public ResponseEntity<DonorProfileDTO> getProfile() {
        return ResponseEntity.ok(donorService.getCurrentDonorProfile());
    }

    @PostMapping("/donate")
    public ResponseEntity<DonorProfileDTO> donate(@RequestParam BigDecimal amount) {
        return ResponseEntity.ok(donorService.donate(amount));
    }
}
