package com.merrymeal.mealsonwheels_backend.service.mealOrderService;

import java.util.List;
import java.util.Optional;

import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.MenuDTO;

public interface MenuService {
    MenuDTO saveMenu(MenuDTO menuDTO);
    Optional<MenuDTO> getMenuById(Long id);
    List<MenuDTO> getAllMenus();
    MenuDTO updateMenu(Long id, MenuDTO menuDTO);
    void deleteMenu(Long id);
}
