package com.merrymeal.mealsonwheels.service.roleService.impl;

import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberDTO;
import com.merrymeal.mealsonwheels.model.CaregiverProfile;
import com.merrymeal.mealsonwheels.model.MemberProfile;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.service.roleService.CaregiverService;
import com.merrymeal.mealsonwheels.util.SecurityUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CaregiverServiceImpl implements CaregiverService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CaregiverDTO getCurrentCaregiverProfile(Long caregiverId) {
        User user = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, "CAREGIVER");

        return mapToCaregiverDTO(user.getCaregiverProfile());
    }

    @Override
    public CaregiverDTO updateCurrentCaregiverProfile(Long caregiverId, CaregiverDTO updatedInfo) {
        User user = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, "CAREGIVER");

        user.setUsername(updatedInfo.getUsername());
        user.setPhoneNumber(updatedInfo.getPhoneNumber());
        user.setEmail(updatedInfo.getEmail());
        return mapToCaregiverDTO(userRepository.save(user).getCaregiverProfile());
    }

    @Override
    public List<MemberDTO> getMembersUnderCare(Long caregiverId) {
        User user = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, "CAREGIVER");

        return user.getCaregiverProfile()
                .getMembersUnderCare().stream()
                .map(this::mapToMemberDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO assignMemberToCurrentCaregiver(Long memberId) {
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

        return mapToMemberDTO(memberProfile);
    }

    // === Mapping Helpers ===

    private CaregiverDTO mapToCaregiverDTO(CaregiverProfile caregiver) {
        CaregiverDTO dto = new CaregiverDTO();
        dto.setId(caregiver.getId());
        dto.setUsername(caregiver.getUser().getUsername());
        dto.setPhoneNumber(caregiver.getUser().getPhoneNumber());
        dto.setEmail(caregiver.getUser().getEmail());
        dto.setApproved(caregiver.getUser().isApproved());
        dto.setRoleName(caregiver.getUser().getRole().name());
        dto.setQualificationAndSkills(caregiver.getQualificationsAndSkills());
        dto.setMemberIds(caregiver.getMembersUnderCare().stream()
                .map(MemberProfile::getId)
                .collect(Collectors.toList()));
        return dto;
    }

    private MemberDTO mapToMemberDTO(MemberProfile member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setUsername(member.getUser().getUsername());
        dto.setPhoneNumber(member.getUser().getPhoneNumber());
        dto.setEmail(member.getUser().getEmail());
        dto.setApproved(member.getUser().isApproved());
        dto.setRoleName(member.getUser().getRole().name());
        dto.setDietaryRestriction(member.getDietaryRestriction());
        dto.setAddress(member.getAddress());
        dto.setMemberLocationLat(member.getMemberLocationLat());
        dto.setMemberLocationLong(member.getMemberLocationLong());
        if (member.getCaregiver() != null) {
            dto.setCaregiverId(member.getCaregiver().getId());
        }
        return dto;
    }
}
