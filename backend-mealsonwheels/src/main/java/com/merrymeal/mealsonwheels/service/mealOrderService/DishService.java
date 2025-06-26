package com.merrymeal.mealsonwheels.service.mealOrder;

import com.merrymeal.mealsonwheels.dto.mealDTOs.DishDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DishService {
    DishDTO saveDish(DishDTO dishDTO);
    Optional<DishDTO> getDishById(Long id);
    List<DishDTO> getAllDishes();
    List<DishDTO> getDishesByMealId(Long mealId);
    List<DishDTO> getDishesByMenuId(Long menuId);
    DishDTO updateDish(Long id, DishDTO dishDTO);
    void deleteDish(Long id);
    void uploadDishPhoto(Long dishId, MultipartFile file) throws IOException;
    byte[] getDishPhoto(Long dishId);
}
