package com.merrymeal.mealsonwheels.dto;

import com.merrymeal.mealsonwheels.dto.roleDTOs.*;
import com.merrymeal.mealsonwheels.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private String phone;
    private String address;

    private Double latitude;
    private Double longitude;

    private Role role;

    private MemberProfileDTO memberProfileDTO;
    private CaregiverProfileDTO caregiverProfileDTO;
    private PartnerProfileDTO partnerProfileDTO;
    private VolunteerProfileDTO volunteerProfileDTO;
    private RiderProfileDTO riderProfileDTO;
    //private SupporterProfileDTO supporterProfileDTO; // ✅ include if applicable
}
