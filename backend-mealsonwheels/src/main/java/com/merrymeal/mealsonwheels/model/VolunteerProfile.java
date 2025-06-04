package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "volunteer_profiles")
public class VolunteerProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    private String volunteerDuration;

    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "available_day")
    private Set<DayOfWeek> availableDays = new HashSet<>();

    public VolunteerProfile() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getVolunteerDuration() {
        return volunteerDuration;
    }

    public void setVolunteerDuration(String volunteerDuration) {
        this.volunteerDuration = volunteerDuration;
    }

    public Set<DayOfWeek> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Set<DayOfWeek> availableDays) {
        this.availableDays = availableDays;
    }
}
