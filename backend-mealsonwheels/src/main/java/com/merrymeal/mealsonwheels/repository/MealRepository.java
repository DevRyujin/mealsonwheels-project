package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByMenuId(Long menuId);

    List<Meal> findByPartnerId(Long partnerId);
}
