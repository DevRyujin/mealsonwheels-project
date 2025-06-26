package com.merrymeal.mealsonwheels.dto.order;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private Double totalAmount;
    private String orderType;
    private String status;
    private LocalDateTime createdAt;

    private Long memberId;
    private String memberUsername;

    private Long partnerId;
    private String partnerName;

    private Long riderId; // Usually the current rider, but good to include for info

    private List<OrderMealDTO> orderMeals;
}
