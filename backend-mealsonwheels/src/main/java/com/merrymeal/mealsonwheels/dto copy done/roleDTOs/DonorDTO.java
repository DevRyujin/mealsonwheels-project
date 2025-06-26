package com.merrymeal.mealsonwheels_backend.dto.roleDTOs;

import com.merrymeal.mealsonwheels_backend.dto.UserDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DonorDTO extends UserDTO {
    private String donorType;
    private BigDecimal totalDonatedAmount;
}
