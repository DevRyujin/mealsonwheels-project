package com.merrymeal.mealsonwheels_backend.dto.roleDTOs;

import com.merrymeal.mealsonwheels_backend.dto.UserDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VolunteerDTO extends UserDTO {
    private String availability;
    private String services;
}
