package com.merrymeal.mealsonwheels_backend.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    private String roleName;

    // Member-specific
    private String address;
    private String dietaryRestriction;

    // Volunteer-specific
    private String availability;
    private String services;

    // Rider-specific
    private String driverLicense;

    // Caregiver-specific
    private String qualificationAndSkills;

    // Partner-specific
    private String companyName;
    private String companyDescription;
    private String companyAddress;

    // Supporter-specific
    private String supportType;
    private String supdescription;

    // Donor-specific
    private String donorType;
    private BigDecimal donationAmount;
}
