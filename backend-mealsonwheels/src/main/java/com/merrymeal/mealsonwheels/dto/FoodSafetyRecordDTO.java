package com.merrymeal.mealsonwheels.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime inspectionDate;

    @NotNull(message = "Safety checklist completion status is required")
    private Boolean safetyChecklistCompleted;

    private String inspectionNotes;

    @NotNull(message = "Ingredient ID is required")
    private Long ingredientId;
}
