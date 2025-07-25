package com.merrymeal.mealsonwheels.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSummaryDTO {

    private Long orderId;
    private String code;
    private String orderType;
    private String status;
    private String restaurantName;
    private String restaurantAddress;
    private String riderName;
    private String riderEmail;
    private Double totalAmount;
    private String createdAt;
}
