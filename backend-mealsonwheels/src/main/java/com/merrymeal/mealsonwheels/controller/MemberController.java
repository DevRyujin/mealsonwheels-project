package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.order.OrderDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderSummaryDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.model.Order;
import com.merrymeal.mealsonwheels.model.Role;
import com.merrymeal.mealsonwheels.security.CustomUserDetails;
import com.merrymeal.mealsonwheels.service.mealOrderService.OrderService;
import com.merrymeal.mealsonwheels.service.roleService.MemberService;
import com.merrymeal.mealsonwheels.service.adminService.ReassessmentEvaluationServiceImpl;
import com.merrymeal.mealsonwheels.dto.ReassessmentEvaluationDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@PreAuthorize("hasRole('MEMBER') or hasRole('CAREGIVER')")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ReassessmentEvaluationServiceImpl evaluationService;
    private final OrderService orderService;

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

}
