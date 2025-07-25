package com.merrymeal.mealsonwheels.dto.order;

import com.merrymeal.mealsonwheels.model.MealType;
import com.merrymeal.mealsonwheels.model.TaskStatus;
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
    private TaskStatus status;
    private LocalDateTime createdAt;

    private Long memberId;
    private String memberUsername;

    private Long partnerId;
    private String partnerName;

    private Long caregiverId;
    private String caregiverName;
    private String caregiverAssistedMember;

    private Long riderId; // Usually the current rider, but good to include for info
    private String riderName;
    private String riderEmail;

    private List<OrderMealDTO> orderMeals;

    private String memberName;           // new
    private String mealName;             // new
    private String restaurant;           // new
    private String restaurantAddress;    // new
    private String code;                 // new
    private LocalDateTime startDeliveryTime; // new (optional)

    private MealType mealType;

    private Integer rating;

    private LocalDateTime orderDate;

    private String memberAddress;
    private String partnerAddress;

    private Double distanceKm;
    private String actualMealType;

}
