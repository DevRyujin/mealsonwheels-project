package com.merrymeal.mealsonwheels.dto.roleDTOs;

import com.merrymeal.mealsonwheels.dto.UserDTO;
import com.merrymeal.mealsonwheels.model.DayOfWeek;
import com.merrymeal.mealsonwheels.model.ServiceType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VolunteerProfileDTO extends UserDTO {

    private ServiceType serviceType;
    private Set<String> availableDays;
    private String volunteerDuration;

    public String getServiceTypeAsString() {
        return serviceType != null ? serviceType.name() : "";
    }
}
