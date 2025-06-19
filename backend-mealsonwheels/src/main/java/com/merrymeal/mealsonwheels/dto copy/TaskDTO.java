package com.merrymeal.mealsonwheels_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Long id;
    private String description;
    private String status;

    private Long volunteerId;
    private String volunteerName;
}
