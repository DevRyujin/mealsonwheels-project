package com.merrymeal.mealsonwheels.dto.order;

import lombok.Data;

@Data
public class AssignRiderRequest {
    private Long orderId;
    private Long riderId;
}

