package com.merrymeal.mealsonwheels.dto.order;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberOrderDTO {

    private Long memberId;
    private String memberName;
    private String address;
    private String contactNumber;

    private Long caregiverId;
    private String caregiverName;
    private String caregiverAddress;
    private String caregiverContactNumber;
    private String assistedMemberName;
    private String assistedMemberAddress;

    private String riderName;
    private String riderEmail;


    private List<OrderSummaryDTO> orders; // A nested list of orders
}
