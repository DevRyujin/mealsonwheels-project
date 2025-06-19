package com.merrymeal.mealsonwheels_backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.merrymeal.mealsonwheels_backend.model.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByMenuId(Long menuId);
    List<Meal> findByPartnerId(Long partnerId);
}
