package com.merrymeal.mealsonwheels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MemberProfileDTO {

    private String dietaryRestrictions;

    private String address;
    private double memberLocationLat;
    private double memberLocationLong;

    private Long caregiverId; // Links back to CaregiverProfile

}
