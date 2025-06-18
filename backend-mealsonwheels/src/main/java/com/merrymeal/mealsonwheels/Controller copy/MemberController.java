package com.merrymeal.mealsonwheels_backend.controller;

import com.merrymeal.mealsonwheels_backend.dto.ReassessmentEvaluationDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.CaregiverDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.MemberDTO;
import com.merrymeal.mealsonwheels_backend.service.adminService.ReassessmentEvaluationServiceImpl;
import com.merrymeal.mealsonwheels_backend.service.roleService.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@PreAuthorize("hasRole('MEMBER')")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReassessmentEvaluationServiceImpl evaluationService;

    @GetMapping("/profile")
    public ResponseEntity<MemberDTO> getProfile() {
        MemberDTO profile = memberService.getMemberProfile();
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<MemberDTO> updateProfile(@RequestBody MemberDTO updatedInfo) {
        MemberDTO updated = memberService.updateMemberProfile(updatedInfo);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/caregiver")
    public ResponseEntity<CaregiverDTO> getAssignedCaregiver() {
        CaregiverDTO caregiver = memberService.getAssignedCaregiver();
        return ResponseEntity.ok(caregiver);
    }

    // 🔹 New endpoint: Submit feedback for a delivered order
    @PostMapping("/order-feedback/{orderId}")
    public ResponseEntity<ReassessmentEvaluationDTO> submitOrderFeedback(
            @PathVariable Long orderId,
            @RequestBody ReassessmentEvaluationDTO dto) {
        ReassessmentEvaluationDTO submitted = evaluationService.submitEvaluationForDeliveredOrder(orderId, dto);
        return ResponseEntity.ok(submitted);
    }
}
