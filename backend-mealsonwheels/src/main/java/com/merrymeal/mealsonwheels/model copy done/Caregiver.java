package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "caregiver")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Caregiver extends User {

    @Column(length = 500)
    private String qualificationsAndSkills;

    @OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> membersUnderCare = new ArrayList<>();

    public Caregiver(String username, String phoneNumber, String email, String password,
                     boolean approved, Role role, String qualificationsAndSkills) {
        super(username, phoneNumber, email, password, approved, role);
        this.qualificationsAndSkills = qualificationsAndSkills;
    }
}
