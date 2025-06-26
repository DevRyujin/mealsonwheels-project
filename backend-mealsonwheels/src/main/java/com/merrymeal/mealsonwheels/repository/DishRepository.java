package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findByMealId(Long mealId);

    List<Dish> findByMenuId(Long menuId);

    Dish findByDishName(String dishName);

    List<Dish> findByPartnerId(Long partnerId);
}
