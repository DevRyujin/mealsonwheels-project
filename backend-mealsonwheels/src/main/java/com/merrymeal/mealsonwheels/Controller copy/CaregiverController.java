package com.merrymeal.mealsonwheels_backend.controller;

import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.CaregiverDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.MemberDTO;
import com.merrymeal.mealsonwheels_backend.service.roleService.CaregiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caregiver")
@PreAuthorize("hasRole('CAREGIVER')")
public class CaregiverController {

    @Autowired
    private CaregiverService caregiverService;

    @GetMapping("/profile")
    public CaregiverDTO getProfile() {
        Long caregiverId = com.merrymeal.mealsonwheels_backend.security.SecurityUtil.getCurrentUserId();
        return caregiverService.getCurrentCaregiverProfile(caregiverId);
    }

    @PutMapping("/profile")
    public CaregiverDTO updateProfile(@RequestBody CaregiverDTO updatedInfo) {
        Long caregiverId = com.merrymeal.mealsonwheels_backend.security.SecurityUtil.getCurrentUserId();
        return caregiverService.updateCurrentCaregiverProfile(caregiverId, updatedInfo);
    }

    @GetMapping("/members")
    public List<MemberDTO> getAssignedMembers() {
        Long caregiverId = com.merrymeal.mealsonwheels_backend.security.SecurityUtil.getCurrentUserId();
        return caregiverService.getMembersUnderCare(caregiverId);
    }

    @PostMapping("/assign-member/{memberId}")
    public MemberDTO assignMemberToSelf(@PathVariable Long memberId) {
        return caregiverService.assignMemberToCurrentCaregiver(memberId);
    }
}
