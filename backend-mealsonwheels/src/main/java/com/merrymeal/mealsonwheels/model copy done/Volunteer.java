package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "volunteer")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Volunteer extends User {

    @Column(nullable = false)
    private String availability;

    @Column(length = 255)
    private String services;

    public Volunteer(String username, String phoneNumber, String email, String password,
                     boolean approved, Role role, String availability, String services) {
        super(username, phoneNumber, email, password, approved, role);
        this.availability = availability;
        this.services = services;
    }
}
