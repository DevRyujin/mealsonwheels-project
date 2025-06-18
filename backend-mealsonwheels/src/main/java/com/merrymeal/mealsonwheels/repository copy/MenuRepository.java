package com.merrymeal.mealsonwheels_backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.merrymeal.mealsonwheels_backend.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByMenuType(String menuType);
    List<Menu> findByPartnerId(Long partnerId);

}
