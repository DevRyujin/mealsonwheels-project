package com.merrymeal.mealsonwheels_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientDTO {

    private Long id;

    @NotBlank(message = "Ingredient name is required")
    @Size(max = 255)
    private String name;

    private LocalDate expirationDate;

    private Double quantity;

    @Size(max = 255)
    private String unit;

    @Size(max = 255)
    private String storageConditions;
}
