package com.merrymeal.mealsonwheels_backend.repository;

import com.merrymeal.mealsonwheels_backend.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    // Custom query to find ingredients expiring before a certain date
    List<Ingredient> findByExpirationDateBefore(LocalDate date);
}
