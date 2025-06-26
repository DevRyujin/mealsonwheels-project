package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.mealDTOs.DishDTO;
import com.merrymeal.mealsonwheels.service.mealOrderService.DishService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.IOException;

@RestController
@RequestMapping("/api/dishes")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('PARTNER') or hasRole('ADMIN')")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping
    public ResponseEntity<DishDTO> createDish(@RequestBody DishDTO dishDTO) {
        return ResponseEntity.ok(dishService.saveDish(dishDTO));
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> getDishById(@PathVariable Long id) {
        return dishService.getDishById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/meal/{mealId}")
    public ResponseEntity<List<DishDTO>> getDishesByMealId(@PathVariable Long mealId) {
        return ResponseEntity.ok(dishService.getDishesByMealId(mealId));
    }

    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<DishDTO>> getDishesByMenuId(@PathVariable Long menuId) {
        return ResponseEntity.ok(dishService.getDishesByMenuId(menuId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishDTO> updateDish(@PathVariable Long id, @RequestBody DishDTO dishDTO) {
        try {
            return ResponseEntity.ok(dishService.updateDish(id, dishDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload-photo")
    public ResponseEntity<String> uploadDishPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            dishService.uploadDishPhoto(id, file);
            return ResponseEntity.ok("Dish photo uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload dish photo.");
        }
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getDishPhoto(@PathVariable Long id) {
        byte[] image = dishService.getDishPhoto(id);
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // adjust for PNG if needed
                .body(image);
    }

}
