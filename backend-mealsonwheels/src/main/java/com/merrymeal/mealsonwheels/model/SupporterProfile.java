package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "supporter_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupporterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String supportType;

    @Column(length = 255)
    private String supDescription;
}
