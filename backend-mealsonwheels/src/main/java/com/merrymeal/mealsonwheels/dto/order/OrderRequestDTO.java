package com.merrymeal.mealsonwheels_backend.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    private Long memberId;
    private Long partnerId;
    private Double totalAmount;
    private Long riderId;

    private List<Long> mealIds; // IDs of meals being ordered
    private List<OrderMealDTO> meals; // Detailed info including photos and quantities
}
