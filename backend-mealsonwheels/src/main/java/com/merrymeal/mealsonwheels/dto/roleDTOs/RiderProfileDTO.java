package com.merrymeal.mealsonwheels.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderProfileDTO {

    private String driverLicenseNumber;
    private LocalDateTime licenseExpiryDate;

    private Long partnerId;
    private String partnerCompanyName;
}
