package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.SupporterDTO;
import com.merrymeal.mealsonwheels_backend.model.Supporter;
import com.merrymeal.mealsonwheels_backend.repository.SupporterRepository;
import com.merrymeal.mealsonwheels_backend.security.SecurityUtil;
import com.merrymeal.mealsonwheels_backend.util.UserValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupporterServiceImpl implements SupporterService {

    private final SupporterRepository supporterRepository;

    @Override
    public SupporterDTO getSupporterProfile() {
        Long userId = SecurityUtil.getCurrentUserId();
        Supporter supporter = supporterRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Supporter not found with ID: " + userId));

        // Add validations
        UserValidationUtil.checkApproved(supporter);
        UserValidationUtil.checkRole(supporter, "SUPPORTER");

        return SupporterDTO.builder()
                .id(supporter.getId())
                .username(supporter.getUsername())
                .email(supporter.getEmail())
                .phoneNumber(supporter.getPhoneNumber())
                .supportType(supporter.getSupportType())
                .supDescription(supporter.getSupDescription())
                .build();
    }
}
