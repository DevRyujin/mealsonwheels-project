package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.CaregiverDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.MemberDTO;
import com.merrymeal.mealsonwheels_backend.model.Caregiver;
import com.merrymeal.mealsonwheels_backend.model.Member;
import com.merrymeal.mealsonwheels_backend.model.User;
import com.merrymeal.mealsonwheels_backend.repository.UserRepository;
import com.merrymeal.mealsonwheels_backend.security.SecurityUtil;
import com.merrymeal.mealsonwheels_backend.util.UserValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final UserRepository userRepository;

    @Override
    public MemberDTO getMemberProfile() {
        Member member = getCurrentMember();
        return mapToMemberDTO(member);
    }

    @Override
    public MemberDTO updateMemberProfile(MemberDTO updatedInfo) {
        Member member = getCurrentMember();

        member.setUsername(updatedInfo.getUsername());
        member.setPhoneNumber(updatedInfo.getPhoneNumber());
        member.setEmail(updatedInfo.getEmail());

        return mapToMemberDTO((Member) userRepository.save(member));
    }

    @Override
    public CaregiverDTO getAssignedCaregiver() {
        Member member = getCurrentMember();
        Caregiver caregiver = member.getCaregiver();

        if (caregiver == null) {
            throw new RuntimeException("No caregiver assigned to this member");
        }

        return mapToCaregiverDTO(caregiver);
    }

    private Member getCurrentMember() {
        Long memberId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found or not authorized"));

        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, "MEMBER");

        return (Member) user;
    }

    private MemberDTO mapToMemberDTO(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .approved(member.isApproved())
                .roleName(member.getRole().getName())
                .dietaryRestriction(member.getDietaryRestriction())
                .address(member.getAddress())
                .memberLocationLat(member.getMemberLocationLat())
                .memberLocationLong(member.getMemberLocationLong())
                .caregiverId(member.getCaregiver() != null ? member.getCaregiver().getId() : null)
                .build();
    }

    private CaregiverDTO mapToCaregiverDTO(Caregiver caregiver) {
        return CaregiverDTO.builder()
                .id(caregiver.getId())
                .username(caregiver.getUsername())
                .phoneNumber(caregiver.getPhoneNumber())
                .email(caregiver.getEmail())
                .approved(caregiver.isApproved())
                .roleName(caregiver.getRole().getName())
                .qualificationAndSkills(caregiver.getQualificationsAndSkills())
                .build();
    }
}
