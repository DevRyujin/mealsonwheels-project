package com.merrymeal.mealsonwheels_backend.controller;

import com.merrymeal.mealsonwheels_backend.dto.OrderDTO;
import com.merrymeal.mealsonwheels_backend.service.roleService.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rider")
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
}
