package com.merrymeal.mealsonwheels_backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.merrymeal.mealsonwheels_backend.model.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByMealId(Long mealId);
    List<Dish> findByMenuId(Long menuId);
    Dish findByDishName(String dishName);
    List<Dish> findByPartnerId(Long partnerId);

}
