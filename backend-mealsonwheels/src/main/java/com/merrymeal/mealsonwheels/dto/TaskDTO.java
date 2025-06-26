package com.merrymeal.mealsonwheels_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Long id;

    @NotBlank
    private String description;

    private String status; // keep as String for flexibility in JSON

    private Long volunteerId;
    private String volunteerName;
}
