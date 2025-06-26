package com.merrymeal.mealsonwheels.service.admin;

import com.merrymeal.mealsonwheels.dto.IngredientDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.Ingredient;
import com.merrymeal.mealsonwheels.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientDTO saveIngredient(IngredientDTO dto) {
        Ingredient ingredient = dtoToEntity(dto);
        Ingredient saved = ingredientRepository.save(ingredient);
        return entityToDto(saved);
    }

    @Override
    public Optional<IngredientDTO> getIngredientById(Long id) {
        return ingredientRepository.findById(id).map(this::entityToDto);
    }

    @Override
    public List<IngredientDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IngredientDTO> getIngredientsExpiringBefore(LocalDate date) {
        return ingredientRepository.findByExpirationDateBefore(date).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public IngredientDTO updateIngredient(Long id, IngredientDTO dto) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id " + id));

        if (dto.getName() != null) ingredient.setName(dto.getName());
        if (dto.getExpirationDate() != null) ingredient.setExpirationDate(dto.getExpirationDate());
        if (dto.getQuantity() != null) ingredient.setQuantity(dto.getQuantity());
        if (dto.getUnit() != null) ingredient.setUnit(dto.getUnit());
        if (dto.getStorageConditions() != null) ingredient.setStorageConditions(dto.getStorageConditions());

        Ingredient updated = ingredientRepository.save(ingredient);
        return entityToDto(updated);
    }

    @Override
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

    // --- Helpers ---
    private Ingredient dtoToEntity(IngredientDTO dto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(dto.getId());
        ingredient.setName(dto.getName());
        ingredient.setExpirationDate(dto.getExpirationDate());
        ingredient.setQuantity(dto.getQuantity());
        ingredient.setUnit(dto.getUnit());
        ingredient.setStorageConditions(dto.getStorageConditions());
        return ingredient;
    }

    private IngredientDTO entityToDto(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());
        dto.setExpirationDate(ingredient.getExpirationDate());
        dto.setQuantity(ingredient.getQuantity());
        dto.setUnit(ingredient.getUnit());
        dto.setStorageConditions(ingredient.getStorageConditions());
        return dto;
    }
}
