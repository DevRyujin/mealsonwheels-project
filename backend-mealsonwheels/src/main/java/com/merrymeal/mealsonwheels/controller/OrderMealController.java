package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.order.OrderRequestDTO;
import com.merrymeal.mealsonwheels.model.Order;
import com.merrymeal.mealsonwheels.repository.OrderRepository;
import com.merrymeal.mealsonwheels.service.mealOrderService.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderMealController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            orderService.placeOrder(orderRequestDTO);
            return ResponseEntity.ok("Order placed successfully.");
        } catch (RuntimeException ex) {
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
}
