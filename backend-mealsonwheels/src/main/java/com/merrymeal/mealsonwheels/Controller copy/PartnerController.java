package com.merrymeal.mealsonwheels_backend.controller;

import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.DishDTO;
import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.MenuDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.RiderDTO;
import com.merrymeal.mealsonwheels_backend.service.roleService.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partner")
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
    public ResponseEntity<List<RiderDTO>> getMyRiders() {
        return ResponseEntity.ok(partnerService.getMyRiders());
    }
}
