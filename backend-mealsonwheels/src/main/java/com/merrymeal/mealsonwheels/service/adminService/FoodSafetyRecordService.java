package com.merrymeal.mealsonwheels.service.adminService;

import com.merrymeal.mealsonwheels.dto.FoodSafetyRecordDTO;

import java.util.List;
import java.util.Optional;

public interface FoodSafetyRecordService {
    FoodSafetyRecordDTO saveRecord(FoodSafetyRecordDTO recordDTO);
    Optional<FoodSafetyRecordDTO> getRecordById(Long id);
    List<FoodSafetyRecordDTO> getAllRecords();
    List<FoodSafetyRecordDTO> getRecordsByIngredientId(Long ingredientId);
    FoodSafetyRecordDTO updateRecord(Long id, FoodSafetyRecordDTO recordDTO);
    void deleteRecord(Long id);
}
