package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_profiles")
public class AdminProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String jobTitle;
    private String department;

    // Add getters/setters
}
