package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.service.roleService.MemberService;
import com.merrymeal.mealsonwheels.service.adminService.ReassessmentEvaluationServiceImpl;
import com.merrymeal.mealsonwheels.dto.ReassessmentEvaluationDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@PreAuthorize("hasRole('MEMBER')")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ReassessmentEvaluationServiceImpl evaluationService;

    @GetMapping("/profile")
    public ResponseEntity<MemberProfileDTO> getProfile() {
        MemberProfileDTO profile = memberService.getMemberProfile();
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<MemberProfileDTO> updateProfile(@RequestBody MemberProfileDTO updatedInfo) {
        MemberProfileDTO updated = memberService.updateMemberProfile(updatedInfo);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/caregiver")
    public ResponseEntity<CaregiverProfileDTO> getAssignedCaregiver() {
        CaregiverProfileDTO caregiver = memberService.getAssignedCaregiver();
        return ResponseEntity.ok(caregiver);
    }

    @PostMapping("/order-feedback/{orderId}")
    public ResponseEntity<ReassessmentEvaluationDTO> submitOrderFeedback(
            @PathVariable Long orderId,
            @RequestBody ReassessmentEvaluationDTO dto) {
        ReassessmentEvaluationDTO submitted = evaluationService.submitEvaluationForDeliveredOrder(orderId, dto);
        return ResponseEntity.ok(submitted);
    }
}
