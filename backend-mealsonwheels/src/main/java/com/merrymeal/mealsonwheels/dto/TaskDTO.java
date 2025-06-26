package com.merrymeal.mealsonwheels.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Long id;

    @NotBlank
    private String description;

    private String taskName;
    private LocalDateTime assignedDate;
    private LocalDateTime dueDate;

    private String status;       // e.g. "PENDING"
    private String statusLabel;  // e.g. "Pending"

    private Long volunteerId;
    private String volunteerName;
}
