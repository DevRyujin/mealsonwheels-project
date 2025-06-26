package com.merrymeal.mealsonwheels.service.mealOrder;

import com.merrymeal.mealsonwheels.dto.mealDTOs.MenuDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.Menu;
import com.merrymeal.mealsonwheels.repository.MenuRepository;
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
    public MenuDTO saveMenu(MenuDTO dto) {
        Menu menu = mapToEntity(dto);
        return mapToDTO(menuRepository.save(menu));
    }

    @Override
    public Optional<MenuDTO> getMenuById(Long id) {
        return menuRepository.findById(id)
                .map(this::mapToDTO);
    }

    @Override
    public List<MenuDTO> getAllMenus() {
        return menuRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuDTO updateMenu(Long id, MenuDTO dto) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));

        menu.setMenuName(dto.getMenuName());
        menu.setMenuType(dto.getMenuType());

        return mapToDTO(menuRepository.save(menu));
    }

    @Override
    public void deleteMenu(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu not found with id: " + id);
        }
        menuRepository.deleteById(id);
    }

    // === Mapping Methods ===

    private MenuDTO mapToDTO(Menu menu) {
        return MenuDTO.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .menuType(menu.getMenuType())
                .build();
    }

    private Menu mapToEntity(MenuDTO dto) {
        return Menu.builder()
                .id(dto.getId())
                .menuName(dto.getMenuName())
                .menuType(dto.getMenuType())
                .build();
    }
}
