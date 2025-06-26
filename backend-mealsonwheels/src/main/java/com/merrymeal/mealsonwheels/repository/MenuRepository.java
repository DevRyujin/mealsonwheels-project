package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByMenuType(String menuType);

    List<Menu> findByPartnerId(Long partnerId); // Assuming Menu has a partnerId field
}
