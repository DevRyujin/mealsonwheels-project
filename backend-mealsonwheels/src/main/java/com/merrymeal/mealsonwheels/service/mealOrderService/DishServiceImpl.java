package com.merrymeal.mealsonwheels.service.mealOrder;

import com.merrymeal.mealsonwheels.dto.mealDTOs.DishDTO;
import com.merrymeal.mealsonwheels.model.Dish;
import com.merrymeal.mealsonwheels.model.Meal;
import com.merrymeal.mealsonwheels.model.Menu;
import com.merrymeal.mealsonwheels.repository.DishRepository;
import com.merrymeal.mealsonwheels.repository.MealRepository;
import com.merrymeal.mealsonwheels.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final MealRepository mealRepository;
    private final MenuRepository menuRepository;

    public DishServiceImpl(DishRepository dishRepository,
                           MealRepository mealRepository,
                           MenuRepository menuRepository) {
        this.dishRepository = dishRepository;
        this.mealRepository = mealRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public DishDTO saveDish(DishDTO dto) {
        Dish dish = mapToEntity(dto);
        dish.setDishCreatedDate(LocalDateTime.now());
        if (dish.getDishPhoto() == null) {
            dish.setDishPhoto(new byte[0]);
        }
        return mapToDTO(dishRepository.save(dish));
    }

    @Override
    public Optional<DishDTO> getDishById(Long id) {
        return dishRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public List<DishDTO> getAllDishes() {
        return dishRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DishDTO> getDishesByMealId(Long mealId) {
        return dishRepository.findByMealId(mealId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DishDTO> getDishesByMenuId(Long menuId) {
        return dishRepository.findByMenuId(menuId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public DishDTO updateDish(Long id, DishDTO dto) {
        return dishRepository.findById(id).map(dish -> {
            dish.setDishName(dto.getDishName());
            dish.setDishDesc(dto.getDishDesc());
            if (dto.getDishPhoto() != null) {
                dish.setDishPhoto(dto.getDishPhoto());
            }
            dish.setDishType(dto.getDishType());
            dish.setDishDietary(dto.getDishDietary());

            if (dto.getMealId() != null) {
                Meal meal = mealRepository.findById(dto.getMealId())
                        .orElseThrow(() -> new RuntimeException("Meal not found"));
                dish.setMeal(meal);
            }

            if (dto.getMenuId() != null) {
                Menu menu = menuRepository.findById(dto.getMenuId())
                        .orElseThrow(() -> new RuntimeException("Menu not found"));
                dish.setMenu(menu);
            }

            return mapToDTO(dishRepository.save(dish));
        }).orElseThrow(() -> new RuntimeException("Dish not found with id " + id));
    }

    @Override
    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public void uploadDishPhoto(Long dishId, MultipartFile file) throws IOException {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found"));
        dish.setDishPhoto(file.getBytes());
        dishRepository.save(dish);
    }

    @Override
    public byte[] getDishPhoto(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found"));
        return dish.getDishPhoto();
    }

    // Helpers
    private DishDTO mapToDTO(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .dishName(dish.getDishName())
                .dishDesc(dish.getDishDesc())
                .dishPhoto(dish.getDishPhoto())
                .dishType(dish.getDishType())
                .dishDietary(dish.getDishDietary())
                .mealId(dish.getMeal() != null ? dish.getMeal().getId() : null)
                .menuId(dish.getMenu() != null ? dish.getMenu().getId() : null)
                .dishCreatedDate(dish.getDishCreatedDate())
                .build();
    }

    private Dish mapToEntity(DishDTO dto) {
        Dish dish = new Dish();
        dish.setDishName(dto.getDishName());
        dish.setDishDesc(dto.getDishDesc());
        dish.setDishPhoto(dto.getDishPhoto());
        dish.setDishType(dto.getDishType());
        dish.setDishDietary(dto.getDishDietary());

        if (dto.getMealId() != null) {
            Meal meal = mealRepository.findById(dto.getMealId())
                    .orElseThrow(() -> new RuntimeException("Meal not found"));
            dish.setMeal(meal);
        }

        if (dto.getMenuId() != null) {
            Menu menu = menuRepository.findById(dto.getMenuId())
                    .orElseThrow(() -> new RuntimeException("Menu not found"));
            dish.setMenu(menu);
        }

        return dish;
    }
}
