package com.merrymeal.mealsonwheels.dto.roleDTOs;

import com.merrymeal.mealsonwheels.dto.UserDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SupporterProfileDTO extends UserDTO {
    private String supportType;
    private String supDescription;
}
