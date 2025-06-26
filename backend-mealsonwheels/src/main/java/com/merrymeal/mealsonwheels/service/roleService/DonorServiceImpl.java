package com.merrymeal.mealsonwheels.service.roleService.impl;

import com.merrymeal.mealsonwheels.dto.roleDTOs.DonorDTO;
import com.merrymeal.mealsonwheels.model.DonorProfile;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.repository.DonorProfileRepository;
import com.merrymeal.mealsonwheels.service.roleService.DonorService;
import com.merrymeal.mealsonwheels.util.SecurityUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {

    private final DonorProfileRepository donorProfileRepository;
    private final UserRepository userRepository;

    @Override
    public DonorDTO getCurrentDonorProfile() {
        DonorProfile donorProfile = getCurrentDonor();
        return mapToDonorDTO(donorProfile);
    }

    @Override
    public DonorDTO donate(BigDecimal amount) {
        DonorProfile donorProfile = getCurrentDonor();
        BigDecimal newTotal = donorProfile.getTotalDonatedAmount().add(amount);
        donorProfile.setTotalDonatedAmount(newTotal);
        donorProfileRepository.save(donorProfile);
        return mapToDonorDTO(donorProfile);
    }

    private DonorProfile getCurrentDonor() {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, "DONOR");

        DonorProfile donorProfile = user.getDonorProfile();
        if (donorProfile == null) {
            throw new RuntimeException("Donor profile not found for user ID: " + userId);
        }
        return donorProfile;
    }

    private DonorDTO mapToDonorDTO(DonorProfile profile) {
        return DonorDTO.builder()
                .id(profile.getId())
                .username(profile.getUser().getUsername())
                .email(profile.getUser().getEmail())
                .donorType(profile.getDonorType())
                .totalDonatedAmount(profile.getTotalDonatedAmount())
                .build();
    }
}
