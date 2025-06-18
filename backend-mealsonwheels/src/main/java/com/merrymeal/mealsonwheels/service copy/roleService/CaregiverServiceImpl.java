package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.CaregiverDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.MemberDTO;
import com.merrymeal.mealsonwheels_backend.model.Caregiver;
import com.merrymeal.mealsonwheels_backend.model.Member;
import com.merrymeal.mealsonwheels_backend.model.User;
import com.merrymeal.mealsonwheels_backend.repository.UserRepository;
import com.merrymeal.mealsonwheels_backend.security.SecurityUtil;
import com.merrymeal.mealsonwheels_backend.util.UserValidationUtil;
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

        return mapToCaregiverDTO((Caregiver) user);
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

        return mapToCaregiverDTO((Caregiver) userRepository.save(user));
    }

    @Override
    public List<MemberDTO> getMembersUnderCare(Long caregiverId) {
        User user = userRepository.findById(caregiverId)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, "CAREGIVER");

        Caregiver caregiver = (Caregiver) user;

        return caregiver.getMembersUnderCare()
                .stream()
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

        if (!(memberUser instanceof Member)) {
            throw new RuntimeException("User is not a member");
        }

        Member member = (Member) memberUser;

        if (member.getCaregiver() != null) {
            throw new RuntimeException("Member already has a caregiver assigned");
        }

        member.setCaregiver((Caregiver) caregiverUser);
        userRepository.save(member);

        return mapToMemberDTO(member);
    }

    private CaregiverDTO mapToCaregiverDTO(Caregiver caregiver) {
        CaregiverDTO dto = new CaregiverDTO();
        dto.setId(caregiver.getId());
        dto.setUsername(caregiver.getUsername());
        dto.setPhoneNumber(caregiver.getPhoneNumber());
        dto.setEmail(caregiver.getEmail());
        dto.setApproved(caregiver.isApproved());
        dto.setRoleName(caregiver.getRole().getName());
        dto.setQualificationAndSkills(caregiver.getQualificationsAndSkills());
        dto.setMemberIds(
                caregiver.getMembersUnderCare().stream()
                        .map(Member::getId)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    private MemberDTO mapToMemberDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setUsername(member.getUsername());
        dto.setPhoneNumber(member.getPhoneNumber());
        dto.setEmail(member.getEmail());
        dto.setApproved(member.isApproved());
        dto.setRoleName(member.getRole().getName());
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
