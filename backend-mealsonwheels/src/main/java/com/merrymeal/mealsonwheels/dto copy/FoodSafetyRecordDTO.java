package com.merrymeal.mealsonwheels_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodSafetyRecordDTO {

    private Long id;

    @NotNull(message = "Inspection date is required")
    private LocalDateTime inspectionDate;

    @NotNull(message = "Safety checklist completion status is required")
    private Boolean safetyChecklistCompleted;

    private String inspectionNotes;

    @NotNull(message = "Ingredient ID is required")
    private Long ingredientId;
}
