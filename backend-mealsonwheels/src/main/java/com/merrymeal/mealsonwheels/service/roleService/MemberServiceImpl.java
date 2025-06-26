package com.merrymeal.mealsonwheels.service.roleService.impl;

import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.model.CaregiverProfile;
import com.merrymeal.mealsonwheels.model.MemberProfile;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.MemberProfileRepository;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.service.roleService.MemberService;
import com.merrymeal.mealsonwheels.util.SecurityUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberProfileRepository memberProfileRepository;
    private final UserRepository userRepository;

    @Override
    public MemberProfileDTO getMemberProfile() {
        MemberProfile profile = getCurrentMemberProfile();
        return mapToMemberDTO(profile);
    }

    @Override
    public MemberProfileDTO updateMemberProfile(MemberProfileDTO updatedInfo) {
        MemberProfile profile = getCurrentMemberProfile();

        profile.getUser().setUsername(updatedInfo.getUsername());
        profile.getUser().setEmail(updatedInfo.getEmail());
        profile.getUser().setPhoneNumber(updatedInfo.getPhoneNumber());

        profile.setDietaryRestriction(updatedInfo.getDietaryRestriction());
        profile.setAddress(updatedInfo.getAddress());
        profile.setMemberLocationLat(updatedInfo.getMemberLocationLat());
        profile.setMemberLocationLong(updatedInfo.getMemberLocationLong());

        return mapToMemberDTO(memberProfileRepository.save(profile));
    }

    @Override
    public CaregiverProfileDTO getAssignedCaregiver() {
        MemberProfile member = getCurrentMemberProfile();
        CaregiverProfile caregiver = member.getCaregiverProfile();

        if (caregiver == null) {
            throw new RuntimeException("No caregiver assigned to this member.");
        }

        return mapToCaregiverDTO(caregiver);
    }

    // Helper method
    private MemberProfile getCurrentMemberProfile() {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, "MEMBER");

        MemberProfile member = user.getMemberProfile();
        if (member == null) {
            throw new RuntimeException("Member profile not found.");
        }

        return member;
    }

    private MemberProfileDTO mapToMemberDTO(MemberProfile member) {
        return MemberProfileDTO.builder()
                .id(member.getId())
                .username(member.getUser().getUsername())
                .email(member.getUser().getEmail())
                .phoneNumber(member.getUser().getPhoneNumber())
                .roleName(member.getUser().getRole().name())
                .approved(member.getUser().isApproved())
                .dietaryRestriction(member.getDietaryRestriction())
                .address(member.getAddress())
                .memberLocationLat(member.getMemberLocationLat())
                .memberLocationLong(member.getMemberLocationLong())
                .caregiverId(member.getCaregiverProfile() != null ? member.getCaregiverProfile().getId() : null)
                .build();
    }

    private CaregiverProfileDTO mapToCaregiverDTO(CaregiverProfile caregiver) {
        return CaregiverProfileDTO.builder()
                .id(caregiver.getId())
                .username(caregiver.getUser().getUsername())
                .email(caregiver.getUser().getEmail())
                .phoneNumber(caregiver.getUser().getPhoneNumber())
                .roleName(caregiver.getUser().getRole().name())
                .approved(caregiver.getUser().isApproved())
                .qualificationAndSkills(caregiver.getQualificationsAndSkills())
                .build();
    }
}
