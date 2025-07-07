package com.merrymeal.mealsonwheels.dto.roleDTOs;

import com.merrymeal.mealsonwheels.dto.mealDTOs.DishDTO;
import com.merrymeal.mealsonwheels.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels.dto.mealDTOs.MenuDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PartnerProfileDTO {

    private String companyName;
    private String partnershipDuration;

    // Optional extended fields from old PartnerDTO
    private String companyDescription;
    private String companyAddress;
    private double companyLocationLat;
    private double companyLocationLong;

    private String email;
    private String address;

    // You can add these if you're planning to expose related menus/meals later
     private List<DishDTO> dishes;
     private List<MealDTO> providedMeals;
     private List<MenuDTO> menus;
}
