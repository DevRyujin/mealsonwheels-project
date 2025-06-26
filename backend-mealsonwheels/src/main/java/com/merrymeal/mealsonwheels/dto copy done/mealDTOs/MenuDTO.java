package com.merrymeal.mealsonwheels_backend.dto.mealDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDTO {
    private Long id;

    @NotBlank(message = "Menu name is required")
    @Size(max = 100)
    private String menuName;

    @NotBlank(message = "Menu type is required")
    @Size(max = 50)
    private String menuType;
    private List<MealDTO> meals; 
    private Long partnerId;
}
