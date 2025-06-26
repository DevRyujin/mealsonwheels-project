package com.merrymeal.mealsonwheels.dto.mealDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealDTO {

    private Long id;

    @NotBlank(message = "Meal name is required")
    @Size(max = 100)
    private String mealName;

    private byte[] mealPhoto;

    @Size(max = 500)
    private String mealDesc;

    private Long partnerId;
    private Long menuId;

    @Size(max = 50)
    private String mealType;

    @Size(max = 50)
    private String mealDietary;

    private LocalDateTime mealCreatedDate;
}
