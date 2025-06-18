package com.merrymeal.mealsonwheels_backend.dto.roleDTOs;

import com.merrymeal.mealsonwheels_backend.dto.UserDTO;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CaregiverDTO extends UserDTO {
    private String qualificationAndSkills;
    private List<Long> memberIds; // Or List<MemberDTO> for more details
}