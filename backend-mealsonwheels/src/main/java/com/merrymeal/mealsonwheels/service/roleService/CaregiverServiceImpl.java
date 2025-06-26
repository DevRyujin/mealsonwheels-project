package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.model.CaregiverProfile;
import com.merrymeal.mealsonwheels.model.MemberProfile;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CaregiverServiceImpl implements CaregiverService {

    private final UserRepository userRepository;

    @Override
    public CaregiverProfileDTO getCurrentCaregiverProfile(Long caregiverId) {
        User user = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, "CAREGIVER");

        return mapToCaregiverProfileDTO(user.getCaregiverProfile());
    }

    @Override
    public CaregiverProfileDTO updateCurrentCaregiverProfile(Long caregiverId, CaregiverProfileDTO updatedInfo) {
        User user = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, "CAREGIVER");

        CaregiverProfile caregiverProfile = user.getCaregiverProfile();

        caregiverProfile.setMemberNameToAssist(updatedInfo.getMemberNameToAssist());
        caregiverProfile.setMemberPhoneNumberToAssist(updatedInfo.getMemberPhoneNumberToAssist());
        caregiverProfile.setMemberAddressToAssist(updatedInfo.getMemberAddressToAssist());
        caregiverProfile.setMemberRelationship(updatedInfo.getMemberRelationship());
        caregiverProfile.setQualificationsAndSkills(updatedInfo.getQualificationsAndSkills());

        return mapToCaregiverProfileDTO(userRepository.save(user).getCaregiverProfile());
    }

    @Override
    public List<MemberProfileDTO> getMembersUnderCare(Long caregiverId) {
        User user = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, "CAREGIVER");

        return user.getCaregiverProfile().getMembersUnderCare()
                .stream()
                .map(this::mapToMemberProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MemberProfileDTO assignMemberToCurrentCaregiver(Long memberId) {
        Long caregiverId = SecurityUtil.getCurrentUserId();

        User caregiverUser = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));
        UserValidationUtil.checkApproved(caregiverUser);
        UserValidationUtil.checkRole(caregiverUser, "CAREGIVER");

        User memberUser = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        MemberProfile memberProfile = memberUser.getMemberProfile();
        if (memberProfile.getCaregiver() != null) {
            throw new RuntimeException("Member already has a caregiver assigned");
        }

        memberProfile.setCaregiver(caregiverUser.getCaregiverProfile());
        userRepository.save(memberUser);

        return mapToMemberProfileDTO(memberProfile);
    }

    // === Mapping Helpers ===

    private CaregiverProfileDTO mapToCaregiverProfileDTO(CaregiverProfile caregiver) {
        return CaregiverProfileDTO.builder()
                .assignedMember(caregiver.getAssignedMember() != null ? caregiver.getAssignedMember().getEmail() : null)
                .memberNameToAssist(caregiver.getMemberNameToAssist())
                .memberPhoneNumberToAssist(caregiver.getMemberPhoneNumberToAssist())
                .memberAddressToAssist(caregiver.getMemberAddressToAssist())
                .memberRelationship(caregiver.getMemberRelationship())
                .qualificationsAndSkills(caregiver.getQualificationsAndSkills())
                .memberIds(caregiver.getMembersUnderCare().stream()
                        .map(MemberProfile::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    private MemberProfileDTO mapToMemberProfileDTO(MemberProfile member) {
        User user = member.getUser();
        if (user == null) {
            throw new RuntimeException("MemberProfile is not linked to a User.");
        }

        return MemberProfileDTO.builder()
                .username(user.getName()) // If username not needed, remove this line
                .email(user.getEmail())   // If missing in DTO, remove this too
                .phoneNumber(user.getPhone())
                .address(member.getAddress())
                .dietaryRestrictions(member.getDietaryRestrictions())  // ✅ Fixed name
                .approved(user.isApproved())
                .memberLocationLat(member.getMemberLocationLat())
                .memberLocationLong(member.getMemberLocationLong())
                .caregiverId(member.getCaregiver() != null ? member.getCaregiver().getId() : null)
                .build();
    }

}
