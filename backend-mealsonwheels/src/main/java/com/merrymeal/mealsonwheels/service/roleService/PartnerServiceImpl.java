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
        return mealRepository.findByPartnerId(partner.getUser().getId())
                .stream()
                .map(this::mapToMealDTO)
                .collect(Collectors.toList());
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
                .map(this::toRiderProfileDTO)
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
                .mealType(meal.getMealType())
                .mealDietary(meal.getMealDietary())
                .mealCreatedDate(meal.getMealCreatedDate())
                .partnerId(meal.getPartner() != null ? meal.getPartner().getId() : null)
                .menuId(meal.getMenu() != null ? meal.getMenu().getId() : null)
                .mealPhotoType(meal.getMealPhotoType())
                .photoData(meal.getMealPhoto() != null
                        ? java.util.Base64.getEncoder().encodeToString(meal.getMealPhoto())
                        : null)
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

    @Override
    public MealDTO createMeal(MealDTO mealDTO) {
        PartnerProfile partner = getCurrentPartner();

        Meal meal = new Meal();
        meal.setMealName(mealDTO.getMealName());
        meal.setMealDesc(mealDTO.getMealDesc());
        meal.setMealType(mealDTO.getMealType());
        meal.setMealDietary(mealDTO.getMealDietary());
        meal.setMealCreatedDate(java.time.LocalDateTime.now());
        meal.setPartner(partner.getUser());

        // Handle photo (Base64 string to byte[] if needed)
        if (mealDTO.getPhotoData() != null) {
            String base64Data = mealDTO.getPhotoData();
            byte[] imageData = java.util.Base64.getDecoder().decode(base64Data.split(",")[1]);
            meal.setMealPhoto(imageData);
            meal.setMealPhotoType(base64Data.split(",")[0].split(":")[1].split(";")[0]); // Extract MIME type
        }

        Meal saved = mealRepository.save(meal);
        return mapToMealDTO(saved);
    }

    @Override
    public void deleteMeal(Long id) {
        PartnerProfile partner = getCurrentPartner();
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal not found with ID: " + id));
        // Check ownership
        if (!meal.getPartner().getId().equals(partner.getUser().getId())) {
            throw new RuntimeException("Not authorized to delete this meal");
        }
        mealRepository.delete(meal);
    }

    @Override
    public MealDTO updateMeal(Long id, MealDTO mealDTO) {
        PartnerProfile partner = getCurrentPartner();
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal not found with ID: " + id));
        // Check ownership
        if (!meal.getPartner().getId().equals(partner.getUser().getId())) {
            throw new RuntimeException("Not authorized to update this meal");
        }

        meal.setMealName(mealDTO.getMealName());
        meal.setMealDesc(mealDTO.getMealDesc());
        meal.setMealType(mealDTO.getMealType());
        meal.setMealDietary(mealDTO.getMealDietary());

        if (mealDTO.getPhotoData() != null) {
            byte[] imageData = java.util.Base64.getDecoder().decode(mealDTO.getPhotoData().split(",")[1]);
            meal.setMealPhoto(imageData);
        }

        Meal updatedMeal = mealRepository.save(meal);
        return mapToMealDTO(updatedMeal);
    }

    private RiderProfileDTO toRiderProfileDTO(RiderProfile rider) {
        return RiderProfileDTO.builder()
                .id(rider.getId())
                .name(rider.getUser().getName())
                .email(rider.getUser().getEmail())
                .phone(rider.getUser().getPhone())
                .approved(rider.getUser().isApproved())
                .driverLicenseNumber(rider.getDriverLicenseNumber())
                .licenseExpiryDate(rider.getLicenseExpiryDate())
                .partnerId(rider.getPartner() != null ? rider.getPartner().getId() : null)
                .partnerCompanyName(rider.getPartner() != null ? rider.getPartner().getCompanyName() : null)
                .build();
    }





}
