package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.mealDTOs.DishDTO;
import com.merrymeal.mealsonwheels.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels.dto.mealDTOs.MenuDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.RiderProfileDTO;

import java.util.List;

public interface PartnerService {
    List<MealDTO> getMyMeals();
    List<DishDTO> getMyDishes();
    List<MenuDTO> getMyMenus();
    List<RiderProfileDTO> getMyRiders();
}
