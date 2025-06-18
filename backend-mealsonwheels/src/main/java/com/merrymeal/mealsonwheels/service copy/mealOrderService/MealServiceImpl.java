package com.merrymeal.mealsonwheels_backend.service.mealOrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.merrymeal.mealsonwheels_backend.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels_backend.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels_backend.model.Meal;
import com.merrymeal.mealsonwheels_backend.model.Partner;
import com.merrymeal.mealsonwheels_backend.repository.MealRepository;
import com.merrymeal.mealsonwheels_backend.repository.PartnerRepository;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final PartnerRepository partnerRepository;

    public MealServiceImpl(MealRepository mealRepository, PartnerRepository partnerRepository) {
        this.mealRepository = mealRepository;
        this.partnerRepository = partnerRepository;
    }

    @Override
    public MealDTO saveMeal(MealDTO mealDTO) {
        Meal meal = mapToEntity(mealDTO);
        meal.setMealCreatedDate(LocalDateTime.now());
        if (meal.getMealPhoto() == null) {
            meal.setMealPhoto(new byte[0]);
        }

        if (mealDTO.getPartnerId() != null) {
            Partner partner = partnerRepository.findById(mealDTO.getPartnerId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Partner not found with id " + mealDTO.getPartnerId()));
            meal.setPartner(partner);
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
    public MealDTO updateMeal(Long id, MealDTO mealDTO) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with id " + id));

        meal.setMealName(mealDTO.getMealName());
        meal.setMealDesc(mealDTO.getMealDesc());
        meal.setMealPhoto(mealDTO.getMealPhoto() != null ? mealDTO.getMealPhoto() : meal.getMealPhoto());
        meal.setMealType(mealDTO.getMealType());
        meal.setMealDietary(mealDTO.getMealDietary());

        if (mealDTO.getPartnerId() != null) {
            Partner partner = partnerRepository.findById(mealDTO.getPartnerId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Partner not found with id " + mealDTO.getPartnerId()));
            meal.setPartner(partner);
        }

        return mapToDTO(mealRepository.save(meal));
    }

    @Override
    public void deleteMeal(Long id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with id " + id));
        mealRepository.delete(meal);
    }

    @Override
    public void uploadMealPhoto(Long mealId, MultipartFile file) throws IOException {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));
        meal.setMealPhoto(file.getBytes());
        mealRepository.save(meal);
    }

    @Override
    public byte[] getMealPhoto(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));
        return meal.getMealPhoto();
    }

    // Helper methods for mapping
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
