package com.merrymeal.mealsonwheels.dto.roleDTOs;

import com.merrymeal.mealsonwheels.dto.UserDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonorProfileDTO {

    private Long id;

    private String donorType;
    private BigDecimal totalDonatedAmount;

    // Optional card details (masked or frontend usage)
    private String cardHolderName;
    private String cardType;
    private String cardNumberMasked;
    private Integer expiryMonth;
    private Integer expiryYear;

    private LocalDateTime lastDonationDate;

    // Embed full user info
    private UserDTO user;
}
