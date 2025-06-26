package com.merrymeal.mealsonwheels.dto.mealDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishDTO {

    private Long id;

    @NotBlank(message = "Dish name is required")
    @Size(max = 100)
    private String dishName;

    private byte[] dishPhoto;

    @Size(max = 500)
    private String dishDesc;

    private Long partnerId;
    private Long mealId;
    private Long menuId;

    @Size(max = 100)
    private String dishType;
    @Size(max = 100)
    private String dishDietary;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dishCreatedDate;
}
