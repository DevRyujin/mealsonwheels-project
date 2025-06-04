package com.merrymeal.mealsonwheels.dto;

import com.merrymeal.mealsonwheels.model.DayOfWeek;
import com.merrymeal.mealsonwheels.model.ServiceType;

import java.util.Set;

public class VolunteerProfileDTO {

    private ServiceType serviceType;
    private Set<DayOfWeek> availableDays;
    private String volunteerDuration;

    public VolunteerProfileDTO() {

    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Set<DayOfWeek> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Set<DayOfWeek> availableDays) {
        this.availableDays = availableDays;
    }

    public String getVolunteerDuration() {
        return volunteerDuration;
    }

    public void setVolunteerDuration(String volunteerDuration) {
        this.volunteerDuration = volunteerDuration;
    }
}
