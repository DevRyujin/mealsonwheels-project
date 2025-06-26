package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.FoodSafetyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodSafetyRecordRepository extends JpaRepository<FoodSafetyRecord, Long> {

    // 🔍 Fetch records by associated ingredient
    List<FoodSafetyRecord> findByIngredientId(Long ingredientId);
}
