package com.merrymeal.mealsonwheels.dto.order;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    private Long caregiverId;
    private Long memberId;
    private Long partnerId;
    private Double totalAmount;
    private Long riderId;

    private List<Long> mealId; // IDs of meals being ordered this is former "mealIds"
    private List<OrderMealDTO> meals; // Detailed info including photos and quantities
}
