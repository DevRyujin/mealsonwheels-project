package com.merrymeal.mealsonwheels.dto.order;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderOrderDTO {

    private Long orderId;
    private String code; // e.g. "ORD-123"

    // Order Information
    private String orderType;
    private String status;

    // Time Information
    private LocalDateTime startDeliveryTime;
    private LocalDateTime endDeliveryTime;

    // Meal details
    private List<OrderMealDTO> meals;

    // Pickup / Restaurant Information
    private String restaurantName;
    private String restaurantAddress;

    // Delivery / Member Information
    private String memberName;
    private String deliveryAddress;
    private String contactNumber;

    private Double estimatedDistanceKm;
    private String estimatedDeliveryTime; // e.g., "15 minutes"
}

