package com.merrymeal.mealsonwheels.service.mealOrderService;

import com.merrymeal.mealsonwheels.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.Meal;
import com.merrymeal.mealsonwheels.model.PartnerProfile;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.MealRepository;
import com.merrymeal.mealsonwheels.repository.PartnerProfileRepository;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final PartnerProfileRepository partnerProfileRepository;
    private final UserRepository userRepository;

    public MealServiceImpl(MealRepository mealRepository,
                           PartnerProfileRepository partnerProfileRepository,
                           UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.partnerProfileRepository = partnerProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MealDTO saveMeal(MealDTO dto) {
        Meal meal = mapToEntity(dto);
        meal.setMealCreatedDate(LocalDateTime.now());

        if (dto.getPartnerId() != null) {
            User partnerUser = userRepository.findById(dto.getPartnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Partner user not found with ID: " + dto.getPartnerId()));
            meal.setPartner(partnerUser);
        }


        return mapToDTO(mealRepository.save(meal));
    }

    @Override
    public List<MealDTO> getAllMeals() {
        return mealRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MealDTO> getMealById(Long id) {
        return mealRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public MealDTO updateMeal(Long id, MealDTO dto) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + id));

        meal.setMealName(dto.getMealName());
        meal.setMealDesc(dto.getMealDesc());
        meal.setMealType(dto.getMealType());
        meal.setMealDietary(dto.getMealDietary());
        if (dto.getMealPhoto() != null) {
            meal.setMealPhoto(dto.getMealPhoto());
        }

        if (dto.getPartnerId() != null) {
            User partnerUser = userRepository.findById(dto.getPartnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Partner user not found with ID: " + dto.getPartnerId()));
            meal.setPartner(partnerUser);
        }

        return mapToDTO(mealRepository.save(meal));
    }

    @Override
    public void deleteMeal(Long id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + id));
        mealRepository.delete(meal);
    }

    @Override
    public void uploadMealPhoto(Long mealId, MultipartFile file) throws IOException {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + mealId));
        meal.setMealPhoto(file.getBytes());
        mealRepository.save(meal);
    }

    @Override
    public byte[] getMealPhoto(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + mealId));
        return meal.getMealPhoto();
    }

    // === Helper Methods ===
    private MealDTO mapToDTO(Meal meal) {
        return MealDTO.builder()
                .id(meal.getId())
                .mealName(meal.getMealName())
                .mealDesc(meal.getMealDesc())
                .mealPhoto(meal.getMealPhoto())
                .mealType(meal.getMealType())
                .mealDietary(meal.getMealDietary())
                .mealCreatedDate(meal.getMealCreatedDate())
                .partnerId(meal.getPartner() != null ? meal.getPartner().getId() : null)
                .menuId(meal.getMenu() != null ? meal.getMenu().getId() : null)
                .build();
    }

    private Meal mapToEntity(MealDTO dto) {
        Meal meal = new Meal();
        meal.setMealName(dto.getMealName());
        meal.setMealDesc(dto.getMealDesc());
        meal.setMealPhoto(dto.getMealPhoto());
        meal.setMealType(dto.getMealType());
        meal.setMealDietary(dto.getMealDietary());
        return meal;
    }
}
