package com.merrymeal.mealsonwheels.service.mealOrderService;

import com.merrymeal.mealsonwheels.dto.mealDTOs.MenuDTO;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    MenuDTO saveMenu(MenuDTO menuDTO);
    Optional<MenuDTO> getMenuById(Long id);
    List<MenuDTO> getAllMenus();
    MenuDTO updateMenu(Long id, MenuDTO menuDTO);
    void deleteMenu(Long id);
}
