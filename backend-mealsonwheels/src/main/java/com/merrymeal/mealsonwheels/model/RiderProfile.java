package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "rider_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String driverLicenseNumber;

    private LocalDateTime licenseExpiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private PartnerProfile partner;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "rider_available_days", joinColumns = @JoinColumn(name = "rider_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private Set<DayOfWeek> availableDays;

    @OneToMany(mappedBy = "rider")
    private List<Order> orders = new ArrayList<>();

}
