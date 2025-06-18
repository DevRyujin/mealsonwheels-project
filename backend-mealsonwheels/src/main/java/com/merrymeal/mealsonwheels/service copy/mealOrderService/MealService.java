package com.merrymeal.mealsonwheels_backend.service.mealOrderService;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.MealDTO;

import java.io.IOException;

public interface MealService {
    MealDTO saveMeal(MealDTO mealDTO);

    List<MealDTO> getAllMeals();

    Optional<MealDTO> getMealById(Long id);

    MealDTO updateMeal(Long id, MealDTO mealDTO);

    void deleteMeal(Long id);

    void uploadMealPhoto(Long mealId, MultipartFile file) throws IOException;

    byte[] getMealPhoto(Long mealId);

}
