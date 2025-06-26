package com.merrymeal.mealsonwheels_backend.dto.roleDTOs;

import com.merrymeal.mealsonwheels_backend.dto.UserDTO;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class SupporterDTO extends UserDTO {
    private String supportType;
    private String supDescription;
}
