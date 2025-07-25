package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.ReassessmentEvaluationDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderRequestDTO;
import com.merrymeal.mealsonwheels.exception.InvalidOrderException;
import com.merrymeal.mealsonwheels.model.Order;
import com.merrymeal.mealsonwheels.model.Role;
import com.merrymeal.mealsonwheels.repository.OrderRepository;
import com.merrymeal.mealsonwheels.security.CustomUserDetails;
import com.merrymeal.mealsonwheels.service.adminService.ReassessmentEvaluationService;
import com.merrymeal.mealsonwheels.service.mealOrderService.OrderService;

import com.merrymeal.mealsonwheels.service.roleService.CaregiverService;
import com.merrymeal.mealsonwheels.service.roleService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderMealController {

    private final OrderService orderService;

    private final CaregiverService caregiverService;

    private final MemberService memberService;

    private final ReassessmentEvaluationService reassessmentEvaluationService;

    @PreAuthorize("hasRole('MEMBER') or hasRole('CAREGIVER')")
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDTO orderRequest) {
        try {
            if (orderRequest.getMemberId() != null) {
                orderService.placeOrder(orderRequest); // ✅ Member order logic
                return ResponseEntity.ok("Order placed successfully by member.");
            } else if (orderRequest.getCaregiverId() != null) {
                orderService.placeOrderForCaregiver(orderRequest); // ✅ Caregiver logic
                return ResponseEntity.ok("Order placed successfully by caregiver.");
            } else {
                throw new InvalidOrderException("Order must have either memberId or caregiverId.");
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Error placing order: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PARTNER')")
    @PostMapping("/{orderId}/assign-rider/{riderId}")
    public ResponseEntity<String> assignRider(@PathVariable Long orderId, @PathVariable Long riderId) {
        try {
            orderService.assignRider(orderId, riderId);
            return ResponseEntity.ok("Rider assigned successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Error assigning rider: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PARTNER')")
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER', 'PARTNER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('MEMBER') or hasRole('CAREGIVER')")
    @GetMapping("/pending/history")
    public ResponseEntity<?> getPendingOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Role role = userDetails.getRole();

            if (role == Role.MEMBER) {
                return ResponseEntity.ok(memberService.getPendingOrders());
            } else if (role == Role.CAREGIVER) {
                return ResponseEntity.ok(memberService.getCaregiverPendingOrders());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized role.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error fetching pending orders: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('MEMBER') or hasRole('CAREGIVER')")
    @GetMapping("/delivered/history")
    public ResponseEntity<?> getDeliveredOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Role role = userDetails.getRole();

            if (role == Role.MEMBER) {
                return ResponseEntity.ok(memberService.getDeliveredOrders());
            } else if (role == Role.CAREGIVER) {
                return ResponseEntity.ok(memberService.getCaregiverDeliveredOrders());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized role.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error fetching pending orders: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('MEMBER') or hasRole('CAREGIVER')")
    @PostMapping("/rate/{orderId}")
    public ResponseEntity<ReassessmentEvaluationDTO> submitOrderFeedback(
            @PathVariable Long orderId,
            @RequestBody ReassessmentEvaluationDTO dto) {
        ReassessmentEvaluationDTO submitted = reassessmentEvaluationService.submitEvaluationForDeliveredOrder(orderId, dto);
        return ResponseEntity.ok(submitted);
    }
}
