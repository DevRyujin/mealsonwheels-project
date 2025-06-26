package com.merrymeal.mealsonwheels.service.admin;

import com.merrymeal.mealsonwheels.dto.IngredientDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IngredientService {
    IngredientDTO saveIngredient(IngredientDTO ingredientDTO);
    Optional<IngredientDTO> getIngredientById(Long id);
    List<IngredientDTO> getAllIngredients();
    List<IngredientDTO> getIngredientsExpiringBefore(LocalDate date);
    IngredientDTO updateIngredient(Long id, IngredientDTO ingredientDTO);
    void deleteIngredient(Long id);
}
