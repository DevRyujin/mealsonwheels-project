package com.merrymeal.mealsonwheels.dto.roleDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MemberProfileDTO {

    private String name;
    private String email;
    private String phone;

    private List<String> dietaryRestrictions;

    private String address;
    private double memberLocationLat;
    private double memberLocationLong;

    private Long caregiverId;

    private boolean approved; // optional: keep if needed by logic

    // ✅ These must be present
    private String caregiverName;
    private String caregiverEmail;
    private String caregiverPhone;
}
