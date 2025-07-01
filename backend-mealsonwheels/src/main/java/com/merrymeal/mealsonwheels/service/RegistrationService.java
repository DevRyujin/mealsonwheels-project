package com.merrymeal.mealsonwheels.service;

import com.merrymeal.mealsonwheels.dto.*;
import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.PartnerProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.VolunteerProfileDTO;
import com.merrymeal.mealsonwheels.model.*;
import com.merrymeal.mealsonwheels.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final CaregiverProfileRepository caregiverProfileRepository;
    private final VolunteerProfileRepository volunteerProfileRepository;
    private final PartnerProfileRepository partnerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository,
                               MemberProfileRepository memberProfileRepository,
                               CaregiverProfileRepository caregiverProfileRepository,
                               VolunteerProfileRepository volunteerProfileRepository,
                               PartnerProfileRepository partnerProfileRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.memberProfileRepository = memberProfileRepository;
        this.caregiverProfileRepository = caregiverProfileRepository;
        this.volunteerProfileRepository = volunteerProfileRepository;
        this.partnerProfileRepository = partnerProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // ❌ Prevent ADMIN registration
        if (request.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("Registration as ADMIN is not allowed.");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setLatitude(request.getLatitude());
        user.setLongitude(request.getLongitude());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setApproved(false); // ❗ default new user to unapproved new bypassing

        userRepository.save(user);

        switch (request.getRole()) {
            case MEMBER -> {
                MemberProfileDTO dto = request.getMemberProfileDTO();
                MemberProfile profile = new MemberProfile();
                profile.setUser(user);
                profile.setDietaryRestrictions(dto.getDietaryRestrictions());
                profile.setAddress(dto.getAddress());
                profile.setMemberLocationLat(dto.getMemberLocationLat());
                profile.setMemberLocationLong(dto.getMemberLocationLong());
                profile.setApproved(dto.isApproved());

                // Optional: link caregiver if provided
                if (dto.getCaregiverId() != null) {
                    CaregiverProfile caregiver = caregiverProfileRepository.findById(dto.getCaregiverId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid caregiver ID"));
                    profile.setCaregiver(caregiver);
                }

                memberProfileRepository.save(profile);
            }
            case CAREGIVER -> {
                CaregiverProfileDTO dto = request.getCaregiverProfileDTO();
                CaregiverProfile profile = new CaregiverProfile();
                profile.setUser(user);

                profile.setMemberNameToAssist(dto.getMemberNameToAssist()); // ✅ FIXED
                profile.setMemberPhoneNumberToAssist(dto.getMemberPhoneNumberToAssist());
                profile.setMemberAddressToAssist(dto.getMemberAddressToAssist());
                profile.setMemberRelationship(dto.getMemberRelationship());
                profile.setQualificationsAndSkills(dto.getQualificationsAndSkills()); // optional

                caregiverProfileRepository.save(profile);
            }

            case VOLUNTEER -> {
                VolunteerProfileDTO dto = request.getVolunteerProfileDTO();
                VolunteerProfile profile = new VolunteerProfile();
                profile.setUser(user);
                profile.setAvailableDays(dto.getAvailableDays());
                profile.setServiceType(dto.getServiceType());
                profile.setVolunteerDuration(dto.getVolunteerDuration());
                volunteerProfileRepository.save(profile);
            }
            case PARTNER -> {
                PartnerProfileDTO dto = request.getPartnerProfileDTO();
                PartnerProfile profile = new PartnerProfile();
                profile.setUser(user);
                profile.setCompanyName(dto.getCompanyName());
                profile.setPartnershipDuration(dto.getPartnershipDuration());
                partnerProfileRepository.save(profile);
            }
            default -> throw new IllegalArgumentException("Unsupported role: " + request.getRole());
        }

        return mapToDTO(user); // Optional mapping method to UserDTO
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .latitude(user.getLatitude())
                .longitude(user.getLongitude())
                .approved(user.isApproved())
                .role(user.getRole())
                .build();
    }


}

