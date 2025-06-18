package com.merrymeal.mealsonwheels_backend.service.mealOrderService;

import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.MenuDTO;
import com.merrymeal.mealsonwheels_backend.model.Menu;
import com.merrymeal.mealsonwheels_backend.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public MenuDTO saveMenu(MenuDTO menuDTO) {
        Menu menu = mapToEntity(menuDTO);
        Menu savedMenu = menuRepository.save(menu);
        return mapToDTO(savedMenu);
    }

    @Override
    public Optional<MenuDTO> getMenuById(Long id) {
        return menuRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public List<MenuDTO> getAllMenus() {
        return menuRepository.findAll()
                             .stream()
                             .map(this::mapToDTO)
                             .collect(Collectors.toList());
    }

    @Override
    public MenuDTO updateMenu(Long id, MenuDTO menuDTO) {
        Menu updatedMenu = menuRepository.findById(id)
            .map(menu -> {
                menu.setMenuName(menuDTO.getMenuName());
                menu.setMenuType(menuDTO.getMenuType());
                // Add more fields here if MenuDTO has more
                return menuRepository.save(menu);
            }).orElseThrow(() -> new RuntimeException("Menu not found with id " + id));
        return mapToDTO(updatedMenu);
    }

    @Override
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    // Helper methods to convert between Menu and MenuDTO

    private MenuDTO mapToDTO(Menu menu) {
        return MenuDTO.builder()
                      .id(menu.getId())
                      .menuName(menu.getMenuName())
                      .menuType(menu.getMenuType())
                      // add other fields here if needed
                      .build();
    }

    private Menu mapToEntity(MenuDTO dto) {
        return Menu.builder()
                   .id(dto.getId())
                   .menuName(dto.getMenuName())
                   .menuType(dto.getMenuType())
                   // add other fields here if needed
                   .build();
    }
}
