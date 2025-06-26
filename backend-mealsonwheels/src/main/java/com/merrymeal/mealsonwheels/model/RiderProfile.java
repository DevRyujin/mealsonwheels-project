package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rider_profiles")
public class RiderProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String driverLicenseNumber;
    private LocalDateTime licenseExpiryDate;

    // Getters & setters omitted for brevity
}
