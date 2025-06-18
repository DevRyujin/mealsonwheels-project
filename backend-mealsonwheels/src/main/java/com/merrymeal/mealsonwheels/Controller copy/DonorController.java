package com.merrymeal.mealsonwheels_backend.controller;

import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.DonorDTO;
import com.merrymeal.mealsonwheels_backend.service.roleService.DonorService;
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
    public ResponseEntity<DonorDTO> getProfile() {
        return ResponseEntity.ok(donorService.getCurrentDonorProfile());
    }

    @PostMapping("/donate")
    public ResponseEntity<DonorDTO> donate(@RequestParam BigDecimal amount) {
        return ResponseEntity.ok(donorService.donate(amount));
    }
}
