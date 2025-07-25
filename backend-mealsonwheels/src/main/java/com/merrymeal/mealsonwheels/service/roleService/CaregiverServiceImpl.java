package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.order.OrderMealDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderRequestDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.*;
import com.merrymeal.mealsonwheels.repository.*;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import com.merrymeal.mealsonwheels.util.DistanceUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;
import com.merrymeal.mealsonwheels.dto.order.OrderDTO;

import java.time.LocalDateTime;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CaregiverServiceImpl implements CaregiverService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private CaregiverProfileRepository caregiverProfileRepository;

    @Autowired
    private PartnerProfileRepository partnerProfileRepository;

    @Autowired
    private RiderProfileRepository riderProfileRepository;


    @Override
    public CaregiverProfileDTO getCurrentCaregiverProfile(Long caregiverId) {
        User user = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, Role.CAREGIVER);

        return mapToCaregiverProfileDTO(user.getCaregiverProfile());
    }

    @Override
    public CaregiverProfileDTO updateCurrentCaregiverProfile(Long caregiverId, CaregiverProfileDTO updatedInfo) {
        User user = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, Role.CAREGIVER);

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
        UserValidationUtil.checkRole(user, Role.CAREGIVER);

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
        UserValidationUtil.checkRole(caregiverUser, Role.CAREGIVER);

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
        User caregiverUser = caregiver.getUser();

        return CaregiverProfileDTO.builder()
                .caregiverName(caregiverUser.getName())
                .caregiverEmail(caregiverUser.getEmail())
                .caregiverPhone(caregiverUser.getPhone())

                .assignedMember(caregiver.getAssignedMember() != null ? caregiver.getAssignedMember().getEmail() : null)
                .memberNameToAssist(caregiver.getMemberNameToAssist())
                .memberPhoneNumberToAssist(caregiver.getMemberPhoneNumberToAssist())
                .memberAddressToAssist(caregiver.getMemberAddressToAssist())
                .memberRelationship(caregiver.getMemberRelationship())
                .qualificationsAndSkills(caregiver.getQualificationsAndSkills())

                .memberIds(caregiver.getMembersUnderCare().stream()
                        .map(member -> member.getUser().getId())
                        .collect(Collectors.toList()))

                .memberDetails(caregiver.getMembersUnderCare().stream()
                        .map(this::mapToMemberProfileDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private MemberProfileDTO mapToMemberProfileDTO(MemberProfile member) {
        User user = member.getUser();
        CaregiverProfile caregiver = member.getCaregiver();
        User caregiverUser = (caregiver != null) ? caregiver.getUser() : null;

        return MemberProfileDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(member.getAddress())
                .dietaryRestrictions(member.getDietaryRestrictions())
                .approved(user.isApproved())
                .memberLocationLat(member.getMemberLocationLat())
                .memberLocationLong(member.getMemberLocationLong())

                .caregiverId(caregiver != null ? caregiver.getId() : null)
                .caregiverName(caregiverUser != null ? caregiverUser.getName() : null)
                .caregiverEmail(caregiverUser != null ? caregiverUser.getEmail() : null)
                .caregiverPhone(caregiverUser != null ? caregiverUser.getPhone() : null)
                .build();
    }

}
