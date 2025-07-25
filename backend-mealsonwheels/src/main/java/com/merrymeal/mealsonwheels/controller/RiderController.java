package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.order.OrderDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.RiderProfileDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.RiderProfile;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import com.merrymeal.mealsonwheels.service.roleService.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rider")
@PreAuthorize("hasRole('RIDER')")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    // GET /api/rider/orders/pending
    @GetMapping("/orders/pending")
    public ResponseEntity<List<OrderDTO>> getPendingOrders() {
        List<OrderDTO> orders = riderService.getPendingOrders();
        return ResponseEntity.ok(orders);
    }

    // GET /api/rider/orders/delivered
    @GetMapping("/orders/delivered")
    public ResponseEntity<List<OrderDTO>> getDeliveredOrders() {
        List<OrderDTO> orders = riderService.getDeliveredOrders();
        return ResponseEntity.ok(orders);
    }

    // GET /api/rider/orders/{id}
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable Long id) {
        OrderDTO order = riderService.getOrderDetails(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/me")
    public ResponseEntity<RiderProfileDTO> getMyProfile() {
        RiderProfileDTO dto = riderService.getCurrentRiderProfileDTO();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/orders/{id}/start")
    public ResponseEntity<String> startDelivery(@PathVariable Long id) {
        riderService.startDelivery(id); // make sure this method exists
        return ResponseEntity.ok("Delivery started");
    }

    @PostMapping("/orders/{id}/complete")
    public ResponseEntity<String> completeDelivery(@PathVariable Long id) {
        riderService.completeDelivery(id); // Implement this method
        return ResponseEntity.ok("Delivery completed");
    }

    @PostMapping("/orders/{id}/accept")
    public ResponseEntity<String> acceptDelivery(@PathVariable Long id) {
        riderService.acceptDelivery(id);
        return ResponseEntity.ok("Delivery accepted");
    }



}
