package com.merrymeal.mealsonwheels.dto.roleDTOs;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderProfileDTO {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;

    private String driverLicenseNumber;
    private LocalDateTime licenseExpiryDate;

    private Long partnerId;
    private String partnerCompanyName;
}
