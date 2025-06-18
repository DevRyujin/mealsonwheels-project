package com.merrymeal.mealsonwheels_backend.repository;

import com.merrymeal.mealsonwheels_backend.model.FoodSafetyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodSafetyRecordRepository extends JpaRepository<FoodSafetyRecord, Long> {
    // Find all safety records for a specific ingredient
    List<FoodSafetyRecord> findByIngredientId(Long ingredientId);
}
