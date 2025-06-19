package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.DishDTO;
import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.MenuDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.RiderDTO;
import com.merrymeal.mealsonwheels_backend.model.*;
import com.merrymeal.mealsonwheels_backend.repository.*;
import com.merrymeal.mealsonwheels_backend.security.SecurityUtil;
import com.merrymeal.mealsonwheels_backend.util.UserValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final DishRepository dishRepository;
    private final MealRepository mealRepository;
    private final MenuRepository menuRepository;

    @Override
    public List<MealDTO> getMyMeals() {
        Partner partner = getCurrentPartner();
        return mealRepository.findByPartnerId(partner.getId()).stream()
                .map(this::mapToMealDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DishDTO> getMyDishes() {
        Partner partner = getCurrentPartner();
        return dishRepository.findByPartnerId(partner.getId()).stream()
                .map(this::mapToDishDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuDTO> getMyMenus() {
        Partner partner = getCurrentPartner();
        return menuRepository.findByPartnerId(partner.getId()).stream()
                .map(this::mapToMenuDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RiderDTO> getMyRiders() {
        Partner partner = getCurrentPartner();
        return partner.getRiders().stream()
                .map(r -> RiderDTO.builder()
                        .id(r.getId())
                        .username(r.getUsername())
                        .phoneNumber(r.getPhoneNumber())
                        .email(r.getEmail())
                        .driverLicenseNumber(r.getDriverLicenseNumber())
                        .partnerId(partner.getId())
                        .build())
                .collect(Collectors.toList());
    }

    private Partner getCurrentPartner() {
        Long userId = SecurityUtil.getCurrentUserId();
        Partner partner = partnerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Partner not found with ID: " + userId));

        UserValidationUtil.checkApproved(partner);
        UserValidationUtil.checkRole(partner, "PARTNER");

        return partner;
    }

    private MealDTO mapToMealDTO(Meal meal) {
        return MealDTO.builder()
                .id(meal.getId())
                .mealName(meal.getMealName())
                .mealDesc(meal.getMealDesc())
                .mealDietary(meal.getMealDietary())
                .mealType(meal.getMealType())
                .mealPhoto(meal.getMealPhoto())
                .mealCreatedDate(meal.getMealCreatedDate())
                .partnerId(meal.getPartner() != null ? meal.getPartner().getId() : null)
                .menuId(meal.getMenu() != null ? meal.getMenu().getId() : null)
                .build();
    }

    private DishDTO mapToDishDTO(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .dishName(dish.getDishName())
                .dishDesc(dish.getDishDesc())
                .dishType(dish.getDishType())
                .dishDietary(dish.getDishDietary())
                .dishPhoto(dish.getDishPhoto())
                .dishCreatedDate(dish.getDishCreatedDate())
                .partnerId(dish.getPartner() != null ? dish.getPartner().getId() : null)
                .mealId(dish.getMeal() != null ? dish.getMeal().getId() : null)
                .menuId(dish.getMenu() != null ? dish.getMenu().getId() : null)
                .build();
    }

    private MenuDTO mapToMenuDTO(Menu menu) {
        List<MealDTO> meals = menu.getMeals().stream()
                .map(this::mapToMealDTO)
                .collect(Collectors.toList());

        return MenuDTO.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .menuType(menu.getMenuType())
                .meals(meals)
                .partnerId(menu.getPartner() != null ? menu.getPartner().getId() : null)
                .build();
    }
}
