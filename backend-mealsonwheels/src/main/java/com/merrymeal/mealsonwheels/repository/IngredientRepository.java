package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // 🔍 Query to find ingredients expiring before a specific date
    List<Ingredient> findByExpirationDateBefore(LocalDate date);
}
