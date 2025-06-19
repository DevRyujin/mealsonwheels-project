package com.merrymeal.mealsonwheels_backend.dto;

import lombok.*;

import java.time.LocalDateTime;

import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.MealDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderMealDTO {

    private Long mealId;
    private String mealName;
    private byte[] mealPhoto;
    private String mealDesc;
    private String mealType;
    private String mealDietary;
    private Integer quantity;
    private LocalDateTime mealCreatedDate;

    public OrderMealDTO(MealDTO meal, Integer quantity) {
        this.mealId = meal.getId();
        this.mealName = meal.getMealName();
        this.mealPhoto = meal.getMealPhoto();
        this.mealDesc = meal.getMealDesc();
        this.mealType = meal.getMealType();
        this.mealDietary = meal.getMealDietary();
        this.mealCreatedDate = meal.getMealCreatedDate();
        this.quantity = quantity;
    }
}
