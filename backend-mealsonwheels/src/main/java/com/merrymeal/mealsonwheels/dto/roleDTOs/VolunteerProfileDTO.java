package com.merrymeal.mealsonwheels.dto;

import com.merrymeal.mealsonwheels.model.DayOfWeek;
import com.merrymeal.mealsonwheels.model.ServiceType;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerProfileDTO {

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
