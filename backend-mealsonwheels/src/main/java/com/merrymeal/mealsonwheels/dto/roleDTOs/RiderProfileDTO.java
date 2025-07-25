package com.merrymeal.mealsonwheels.dto.roleDTOs;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderProfileDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;

    private boolean approved;
    private List<String> availableDays = new ArrayList<>();

    private String driverLicenseNumber;
    private LocalDateTime licenseExpiryDate;

    private Long partnerId;
    private String partnerCompanyName;

    private Double latitude;
    private Double longitude;


}
