package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.ElementCollection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    // For Geolocation
    private String address;
    private Double latitude;
    private Double longitude;

    private String password;

    @ElementCollection
    private List<String> dietaryRestrictions;

    @Column
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean approved = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private MemberProfile memberProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CaregiverProfile caregiverProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private VolunteerProfile volunteerProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PartnerProfile partnerProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private DonorProfile donorProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AdminProfile adminProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private RiderProfile riderProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private SupporterProfile supporterProfile; // ✅ Added supporter profile

}
