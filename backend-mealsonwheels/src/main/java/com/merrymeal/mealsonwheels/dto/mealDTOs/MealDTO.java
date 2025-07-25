package com.merrymeal.mealsonwheels.dto.mealDTOs;

import com.merrymeal.mealsonwheels.model.MealType;
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

    private byte[] mealPhoto; // used internally
    private String photoData; // used for upload
    private String mealPhotoType; // MIME type e.g., "image/png"

    @Size(max = 500)
    private String mealDesc;

    private Long partnerId;
    private Long menuId;

    private MealType mealType;

    @Size(max = 50)
    private String mealDietary;

    private LocalDateTime mealCreatedDate;

    private Double partnerLatitude;
    private Double partnerLongitude;

}
