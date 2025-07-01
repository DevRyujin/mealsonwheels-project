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
public class CaregiverProfileDTO {

    private String caregiverName;
    private String caregiverEmail;
    private String caregiverPhone;

    private String assignedMember;
    private String memberNameToAssist;
    private String memberPhoneNumberToAssist;
    private String memberAddressToAssist;
    private String memberRelationship;

    private String qualificationsAndSkills;
    private List<Long> memberIds; // Optional if you want to list members under care
    private List<MemberProfileDTO> memberDetails;

    private boolean approved;

}
