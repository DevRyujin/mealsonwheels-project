package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.roleDTOs.SupporterProfileDTO;
import com.merrymeal.mealsonwheels.model.Role;
import com.merrymeal.mealsonwheels.model.SupporterProfile;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.SupporterProfileRepository;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupporterServiceImpl implements SupporterService {

    private final SupporterProfileRepository supporterRepository;

    @Override
    public SupporterProfileDTO getSupporterProfile() {
        Long userId = SecurityUtil.getCurrentUserId();

        SupporterProfile supporter = supporterRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Supporter not found for user ID: " + userId));

        // Validation on user
        User user = supporter.getUser();
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, Role.SUPPORTER);

        return SupporterProfileDTO.builder()
                .id(user.getId()) // user ID
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .latitude(user.getLatitude())
                .longitude(user.getLongitude())
                .role(user.getRole())
                .approved(user.isApproved())
                .supportType(supporter.getSupportType())
                .supDescription(supporter.getSupDescription())
                .build();
    }

}
