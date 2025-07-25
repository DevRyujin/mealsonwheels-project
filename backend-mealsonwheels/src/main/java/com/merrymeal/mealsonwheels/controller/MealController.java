package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import com.merrymeal.mealsonwheels.service.mealOrderService.MealService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.IOException;

@RestController
@RequestMapping("/api/meals")
@CrossOrigin(origins = "*")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    // ✅ PUBLIC: Anyone logged in can see all meals
    @GetMapping
    public ResponseEntity<List<MealDTO>> getAllMeals() {
        return ResponseEntity.ok(mealService.getAllMeals());
    }

    // ✅ PUBLIC: Can view a single meal detail
    @GetMapping("/{id}")
    public ResponseEntity<MealDTO> getMealById(@PathVariable Long id) {
        return mealService.getMealById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ PUBLIC: Used by <img src> — must not be blocked
    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getMealPhoto(@PathVariable Long id) {
        byte[] image = mealService.getMealPhoto(id);
        String contentType = mealService.getMealPhotoType(id);
        return ResponseEntity.ok()
                .header("Content-Type", contentType != null ? contentType : "application/octet-stream")
                .body(image);
    }

    // 🔒 PARTNER/ADMIN only: Create a meal
    @PreAuthorize("hasRole('PARTNER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MealDTO> createMeal(@RequestBody MealDTO mealDTO) {
        Long userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(mealService.saveMeal(mealDTO, userId));
    }

    // 🔒 PARTNER/ADMIN only: Update a meal
    @PreAuthorize("hasRole('PARTNER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MealDTO> updateMeal(@PathVariable Long id, @RequestBody MealDTO mealDTO) {
        try {
            return ResponseEntity.ok(mealService.updateMeal(id, mealDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔒 PARTNER/ADMIN only: Delete a meal
    @PreAuthorize("hasRole('PARTNER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }

    // 🔒 PARTNER/ADMIN only: Upload a meal photo
    @PreAuthorize("hasRole('PARTNER') or hasRole('ADMIN')")
    @PostMapping("/{id}/upload-photo")
    public ResponseEntity<String> uploadMealPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            mealService.uploadMealPhoto(id, file);
            return ResponseEntity.ok("Meal photo uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload meal photo.");
        }
    }

    // 👇 PUBLIC: Get meals filtered by distance (for a member)
    @GetMapping("/member/{memberId}/filtered")
    public ResponseEntity<List<MealDTO>> getMealsByDistanceForMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(mealService.getMealsByDistanceForMember(memberId));
    }

    // 👇 PUBLIC: Get meals filtered by distance (for a caregiver)
    @GetMapping("/caregiver/{caregiverId}/filtered")
    public ResponseEntity<List<MealDTO>> getMealsByDistanceForCaregiver(@PathVariable Long caregiverId) {
        return ResponseEntity.ok(mealService.getMealsByDistanceForCaregiver(caregiverId));
    }

}

