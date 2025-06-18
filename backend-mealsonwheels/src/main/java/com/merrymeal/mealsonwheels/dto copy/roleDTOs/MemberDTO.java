package com.merrymeal.mealsonwheels_backend.dto.roleDTOs;

import com.merrymeal.mealsonwheels_backend.dto.UserDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MemberDTO extends UserDTO {
    private String dietaryRestriction;
    private String address;
    private double memberLocationLat;
    private double memberLocationLong;
    private Long caregiverId;
}
