package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.mealDTOs.MealDTO;
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
@PreAuthorize("hasRole('PARTNER') or hasRole('ADMIN')")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping
    public ResponseEntity<MealDTO> createMeal(@RequestBody MealDTO mealDTO) {
        return ResponseEntity.ok(mealService.saveMeal(mealDTO));
    }

    @GetMapping
    public ResponseEntity<List<MealDTO>> getAllMeals() {
        return ResponseEntity.ok(mealService.getAllMeals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDTO> getMealById(@PathVariable Long id) {
        return mealService.getMealById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealDTO> updateMeal(@PathVariable Long id, @RequestBody MealDTO mealDTO) {
        try {
            return ResponseEntity.ok(mealService.updateMeal(id, mealDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload-photo")
    public ResponseEntity<String> uploadMealPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            mealService.uploadMealPhoto(id, file);
            return ResponseEntity.ok("Meal photo uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload meal photo.");
        }
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getMealPhoto(@PathVariable Long id) {
        byte[] image = mealService.getMealPhoto(id);
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // adjust for PNG if needed
                .body(image);
    }

}
