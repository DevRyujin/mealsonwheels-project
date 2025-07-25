package com.merrymeal.mealsonwheels.service.mealOrderService;

import com.merrymeal.mealsonwheels.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels.model.Meal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MealService {
    MealDTO saveMeal(MealDTO mealDTO, Long partnerId);
    List<MealDTO> getAllMeals();
    Optional<MealDTO> getMealById(Long id);
    MealDTO updateMeal(Long id, MealDTO mealDTO);
    void deleteMeal(Long id);
    void uploadMealPhoto(Long mealId, MultipartFile file) throws IOException;
    byte[] getMealPhoto(Long mealId);

    Meal getMealEntityById(Long mealId);
    String getMealPhotoType(Long mealId);

    List<MealDTO> getMealsByDistanceForMember(Long memberId);

    List<MealDTO> getMealsByDistanceForCaregiver(Long caregiverId);
}
