package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.order.OrderDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderRequestDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.repository.CaregiverProfileRepository;
import com.merrymeal.mealsonwheels.repository.MemberProfileRepository;
import com.merrymeal.mealsonwheels.repository.PartnerProfileRepository;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import com.merrymeal.mealsonwheels.service.roleService.CaregiverService;

import com.merrymeal.mealsonwheels.service.roleService.MemberService;
import com.merrymeal.mealsonwheels.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caregiver")
@PreAuthorize("hasRole('CAREGIVER')")
@CrossOrigin(origins = "http://localhost:5173") // Optional based on frontend setup
public class CaregiverController {

    private final CaregiverService caregiverService;
    private final MemberService memberService;

    @Autowired
    private CaregiverProfileRepository caregiverProfileRepository;

    @Autowired
    private MemberProfileRepository memberProfileRepository;

    @Autowired
    private PartnerProfileRepository partnerProfileRepository;

    public CaregiverController(CaregiverService caregiverService, MemberService memberService) {
        this.caregiverService = caregiverService;
        this.memberService = memberService;
    }

    @GetMapping("/profile")
    public CaregiverProfileDTO getProfile() {
        Long caregiverId = SecurityUtil.getCurrentUserId();
        return caregiverService.getCurrentCaregiverProfile(caregiverId);
    }

    @PutMapping("/profile")
    public CaregiverProfileDTO updateProfile(@RequestBody CaregiverProfileDTO updatedInfo) {
        Long caregiverId = SecurityUtil.getCurrentUserId();
        return caregiverService.updateCurrentCaregiverProfile(caregiverId, updatedInfo);
    }

    @GetMapping("/members")
    public List<MemberProfileDTO> getAssignedMembers() {
        Long caregiverId = SecurityUtil.getCurrentUserId();
        return caregiverService.getMembersUnderCare(caregiverId);
    }

    @PostMapping("/assign-member/{memberId}")
    public MemberProfileDTO assignMemberToSelf(@PathVariable Long memberId) {
        return caregiverService.assignMemberToCurrentCaregiver(memberId);
    }

}
