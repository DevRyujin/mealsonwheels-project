package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.*;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.RiderDTO;

import java.util.List;

public interface PartnerService {

    List<MealDTO> getMyMeals();

    List<DishDTO> getMyDishes();

    List<MenuDTO> getMyMenus();

    List<RiderDTO> getMyRiders();

}
