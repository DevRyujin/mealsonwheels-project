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
    private Set<DayOfWeek> availableDays;
    private String volunteerDuration;

    // Optional legacy-style support (if needed)
    public String getAvailableDaysAsString() {
        return availableDays != null ? String.join(", ", availableDays.stream().map(Enum::name).toList()) : "";
    }

    public String getServiceTypeAsString() {
        return serviceType != null ? serviceType.name() : "";
    }
}
