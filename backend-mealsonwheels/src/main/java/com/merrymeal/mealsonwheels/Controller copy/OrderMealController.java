package com.merrymeal.mealsonwheels_backend.controller;

import com.merrymeal.mealsonwheels_backend.dto.OrderRequestDTO;
import com.merrymeal.mealsonwheels_backend.model.Order;
import com.merrymeal.mealsonwheels_backend.repository.OrderRepository;
import com.merrymeal.mealsonwheels_backend.service.mealOrderService.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderMealController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

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
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER', 'PARTNER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return orderRepository.findById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
