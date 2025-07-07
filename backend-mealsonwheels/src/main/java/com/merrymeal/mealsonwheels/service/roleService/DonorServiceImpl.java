package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.UserDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.DonorProfileDTO;
import com.merrymeal.mealsonwheels.model.DonorProfile;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.model.Role;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.repository.DonorProfileRepository;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {

    private final DonorProfileRepository donorProfileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public DonorProfileDTO getCurrentDonorProfile() {
        DonorProfile donorProfile = getCurrentDonor();
        return mapToDonorDTO(donorProfile);
    }

    @Override
    public DonorProfileDTO donate(BigDecimal amount) {
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
        UserValidationUtil.checkRole(user, Role.DONOR);

        DonorProfile donorProfile = user.getDonorProfile();
        if (donorProfile == null) {
            throw new RuntimeException("Donor profile not found for user ID: " + userId);
        }
        return donorProfile;
    }

    private DonorProfileDTO mapToDonorDTO(DonorProfile profile) {
        User user = profile.getUser();

        return DonorProfileDTO.builder()
                .id(profile.getId())
                .donorType(profile.getDonorType())
                .totalDonatedAmount(profile.getTotalDonatedAmount())
                .cardHolderName(profile.getCardHolderName())
                .cardType(profile.getCardType())
                .cardNumberMasked(profile.getCardNumberMasked())
                .expiryMonth(profile.getExpiryMonth())
                .expiryYear(profile.getExpiryYear())
                .lastDonationDate(profile.getLastDonationDate())
                .user(UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .address(user.getAddress())
                        .latitude(user.getLatitude())
                        .longitude(user.getLongitude())
                        .role(user.getRole())
                        .approved(user.isApproved())
                        .build())
                .build();
    }

    @Override
    public void processDonation(DonorProfileDTO dto) {
        BigDecimal amount = dto.getTotalDonatedAmount();
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Invalid donation amount.");
        }

        DonorProfile donorProfile;
        User user;

        if (dto.getUser() != null && dto.getUser().getEmail() != null) {
            String email = dto.getUser().getEmail();

            // Try to find existing user
            user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {
                // Create new user for donation
                user = User.builder()
                        .name(dto.getUser().getName())
                        .email(email)
                        .phone(dto.getUser().getPhone())
                        .address(dto.getUser().getAddress())
                        .role(Role.DONOR)
                        .approved(true) // Assume approved for donation
                        .build();
            }

            // Get or create donor profile
            donorProfile = donorProfileRepository.findByUserId(user.getId()).orElse(null);

            if (donorProfile == null) {
                donorProfile = DonorProfile.builder()
                        .user(user)
                        .donorType(dto.getDonorType())
                        .build();
            }

        } else {
            throw new RuntimeException("User information is required for donations.");
        }

        // Add donation and card info
        donorProfile.addDonation(amount);
        donorProfile.setCardHolderName(dto.getCardHolderName());
        donorProfile.setCardType(dto.getCardType());
        donorProfile.setCardNumberMasked(dto.getCardNumberMasked());
        donorProfile.setExpiryMonth(dto.getExpiryMonth());
        donorProfile.setExpiryYear(dto.getExpiryYear());

        userRepository.save(user);               // Save or update user
        donorProfileRepository.save(donorProfile); // Save or update donor profile
    }




}
