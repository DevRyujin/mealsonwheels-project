package com.merrymeal.mealsonwheels_backend.dto.roleDTOs;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import com.merrymeal.mealsonwheels_backend.dto.UserDTO;
import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.DishDTO;
import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.MenuDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PartnerDTO extends UserDTO {
    private String companyName;
    private String companyDescription;
    private String companyAddress;
    private double companyLocationLat;
    private double companyLocationLong;
    private List<DishDTO> dishes;
    private List<MealDTO> providedMeals;
    private List<MenuDTO> menus;
}