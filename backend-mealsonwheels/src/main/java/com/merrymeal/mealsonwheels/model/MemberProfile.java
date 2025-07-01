package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "member_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    @CollectionTable(name = "member_dietary_restrictions", joinColumns = @JoinColumn(name = "member_profile_id"))
    @Column(name = "restriction")
    private List<String> dietaryRestrictions;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id")
    private CaregiverProfile caregiver;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double memberLocationLat;

    @Column(nullable = false)
    private double memberLocationLong;

    @Column(nullable = false)
    private boolean approved = false;
}
