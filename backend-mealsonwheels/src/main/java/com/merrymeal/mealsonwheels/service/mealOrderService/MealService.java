package com.merrymeal.mealsonwheels.service.mealOrder;

import com.merrymeal.mealsonwheels.dto.mealDTOs.MealDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MealService {
    MealDTO saveMeal(MealDTO mealDTO);
    List<MealDTO> getAllMeals();
    Optional<MealDTO> getMealById(Long id);
    MealDTO updateMeal(Long id, MealDTO mealDTO);
    void deleteMeal(Long id);
    void uploadMealPhoto(Long mealId, MultipartFile file) throws IOException;
    byte[] getMealPhoto(Long mealId);
}
