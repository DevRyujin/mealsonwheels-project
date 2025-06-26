package com.merrymeal.mealsonwheels.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminStatisticDTO {
    private long totalMembers;
    private long totalPartners;
    private long totalVolunteers;
    private long totalRiders;
    private long totalDonors;
    private long mealsServed;
    private long ordersDelivered;
    private BigDecimal totalDonationsReceived;
}
