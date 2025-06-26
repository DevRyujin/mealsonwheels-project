package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "caregiver_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaregiverProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_member_id")
    private User assignedMember;

    @Column(length = 500)
    private String qualificationsAndSkills;

    private String memberNameToAssist;
    private String memberPhoneNumberToAssist;
    private String memberAddressToAssist;
    private String memberRelationship;

    private boolean approved = false; // default to false

    @OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberProfile> membersUnderCare = new ArrayList<>();
}
