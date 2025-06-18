package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.DonorDTO;
import com.merrymeal.mealsonwheels_backend.model.Donor;
import com.merrymeal.mealsonwheels_backend.repository.DonorRepository;
import com.merrymeal.mealsonwheels_backend.security.SecurityUtil;
import com.merrymeal.mealsonwheels_backend.util.UserValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;

    @Override
    public DonorDTO getCurrentDonorProfile() {
        Donor donor = getCurrentDonor();
        return mapToDonorDTO(donor);
    }

    @Override
    public DonorDTO donate(BigDecimal amount) {
        Donor donor = getCurrentDonor();
        donor.addDonation(amount);
        donorRepository.save(donor);
        return mapToDonorDTO(donor);
    }

    private Donor getCurrentDonor() {
        Long userId = SecurityUtil.getCurrentUserId();
        Donor donor = donorRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Donor not found with ID: " + userId));

        UserValidationUtil.checkApproved(donor);
        UserValidationUtil.checkRole(donor, "DONOR");

        return donor;
    }

    private DonorDTO mapToDonorDTO(Donor donor) {
        return DonorDTO.builder()
                .id(donor.getId())
                .username(donor.getUsername())
                .email(donor.getEmail())
                .donorType(donor.getDonorType())
                .totalDonatedAmount(donor.getTotalDonatedAmount())
                .build();
    }
}
