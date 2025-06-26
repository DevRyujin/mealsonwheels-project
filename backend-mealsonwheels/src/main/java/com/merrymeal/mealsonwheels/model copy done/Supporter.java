package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "supporter")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Supporter extends User {

    @Column(nullable = false)
    private String supportType;

    @Column(length = 255)
    private String supDescription;

    public Supporter(String username, String phoneNumber, String email, String password,
                     boolean approved, Role role, String supportType, String supDescription) {
        super(username, phoneNumber, email, password, approved, role);
        this.supportType = supportType;
        this.supDescription = supDescription;
    }
}
