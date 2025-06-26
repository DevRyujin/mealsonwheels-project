package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.model.CaregiverProfile;
import com.merrymeal.mealsonwheels.model.MemberProfile;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.MemberProfileRepository;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
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

        User user = profile.getUser();
        user.setName(updatedInfo.getUsername());
        user.setEmail(updatedInfo.getEmail());
        user.setPhone(updatedInfo.getPhoneNumber());

        profile.setDietaryRestrictions(updatedInfo.getDietaryRestrictions());
        profile.setAddress(updatedInfo.getAddress());
        profile.setMemberLocationLat(updatedInfo.getMemberLocationLat());
        profile.setMemberLocationLong(updatedInfo.getMemberLocationLong());

        return mapToMemberDTO(memberProfileRepository.save(profile));
    }

    @Override
    public CaregiverProfileDTO getAssignedCaregiver() {
        MemberProfile member = getCurrentMemberProfile();
        CaregiverProfile caregiver = member.getCaregiver();

        if (caregiver == null) {
            throw new RuntimeException("No caregiver assigned to this member.");
        }

        return mapToCaregiverDTO(caregiver);
    }

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
        User user = member.getUser();

        return MemberProfileDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhone())
                .approved(user.isApproved())
                .dietaryRestrictions(member.getDietaryRestrictions())
                .address(member.getAddress())
                .memberLocationLat(member.getMemberLocationLat())
                .memberLocationLong(member.getMemberLocationLong())
                .caregiverId(member.getCaregiver() != null ? member.getCaregiver().getId() : null)
                .build();
    }

    private CaregiverProfileDTO mapToCaregiverDTO(CaregiverProfile caregiver) {
        User user = caregiver.getUser();

        return CaregiverProfileDTO.builder()
                .assignedMember(user.getEmail())
                .memberNameToAssist(caregiver.getMemberNameToAssist())
                .memberPhoneNumberToAssist(caregiver.getMemberPhoneNumberToAssist())
                .memberAddressToAssist(caregiver.getMemberAddressToAssist())
                .memberRelationship(caregiver.getMemberRelationship())
                .qualificationsAndSkills(caregiver.getQualificationsAndSkills())
                .build();
    }
}
