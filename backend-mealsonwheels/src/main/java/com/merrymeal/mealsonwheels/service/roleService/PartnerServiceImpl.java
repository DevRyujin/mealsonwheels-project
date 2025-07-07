package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.mealDTOs.DishDTO;
import com.merrymeal.mealsonwheels.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels.dto.mealDTOs.MenuDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.RiderProfileDTO;
import com.merrymeal.mealsonwheels.model.*;
import com.merrymeal.mealsonwheels.repository.*;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerProfileRepository partnerProfileRepository;
    private final DishRepository dishRepository;
    private final MealRepository mealRepository;
    private final MenuRepository menuRepository;

    @Override
    public List<MealDTO> getMyMeals() {
        PartnerProfile partner = getCurrentPartner();
        return mealRepository.findByPartnerId(partner.getId())
                .stream().map(this::mapToMealDTO).collect(Collectors.toList());
    }

    @Override
    public List<DishDTO> getMyDishes() {
        PartnerProfile partner = getCurrentPartner();
        return dishRepository.findByPartnerId(partner.getId())
                .stream().map(this::mapToDishDTO).collect(Collectors.toList());
    }

    @Override
    public List<MenuDTO> getMyMenus() {
        PartnerProfile partner = getCurrentPartner();
        return menuRepository.findByPartnerId(partner.getId())
                .stream().map(this::mapToMenuDTO).collect(Collectors.toList());
    }

    @Override
    public List<RiderProfileDTO> getMyRiders() {
        PartnerProfile partner = getCurrentPartner();
        return partner.getRiders().stream()
                .map(r -> RiderProfileDTO.builder()
                        .id(r.getId())
                        .username(r.getUser().getName())
                        .email(r.getUser().getEmail())
                        .phoneNumber(r.getUser().getPhone())
                        .driverLicenseNumber(r.getDriverLicenseNumber())
                        .partnerId(partner.getId())
                        .build())
                .collect(Collectors.toList());
    }

    private PartnerProfile getCurrentPartner() {
        Long userId = SecurityUtil.getCurrentUserId();

        PartnerProfile partnerProfile = partnerProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Partner profile not found"));

        User user = partnerProfile.getUser();
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, Role.PARTNER);

        return partnerProfile;
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
