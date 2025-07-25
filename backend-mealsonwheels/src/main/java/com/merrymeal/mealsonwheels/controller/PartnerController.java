package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.mealDTOs.DishDTO;
import com.merrymeal.mealsonwheels.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels.dto.mealDTOs.MenuDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.RiderProfileDTO;
import com.merrymeal.mealsonwheels.service.roleService.PartnerService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partner")
@PreAuthorize("hasRole('PARTNER')")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping("/meals")
    public ResponseEntity<List<MealDTO>> getMyMeals() {
        return ResponseEntity.ok(partnerService.getMyMeals());
    }

    @GetMapping("/dishes")
    public ResponseEntity<List<DishDTO>> getMyDishes() {
        return ResponseEntity.ok(partnerService.getMyDishes());
    }

    @GetMapping("/menus")
    public ResponseEntity<List<MenuDTO>> getMyMenus() {
        return ResponseEntity.ok(partnerService.getMyMenus());
    }

    @GetMapping("/riders")
    public ResponseEntity<List<RiderProfileDTO>> getMyRiders() {
        return ResponseEntity.ok(partnerService.getMyRiders());
    }

    @DeleteMapping("/meals/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        partnerService.deleteMeal(id); // Implement this in the service
        return ResponseEntity.ok().build();
    }

    @PutMapping("/meals/{id}")
    public ResponseEntity<MealDTO> updateMeal(@PathVariable Long id, @RequestBody MealDTO mealDTO) {
        MealDTO updated = partnerService.updateMeal(id, mealDTO); // Implement this
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/meals")
    public ResponseEntity<MealDTO> createMeal(@RequestBody MealDTO mealDTO) {
        MealDTO created = partnerService.createMeal(mealDTO); // You’ll create this
        return ResponseEntity.ok(created);
    }

}
