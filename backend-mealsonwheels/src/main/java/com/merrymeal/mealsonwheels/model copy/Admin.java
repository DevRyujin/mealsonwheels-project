package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "user_id")
@NoArgsConstructor
public class Admin extends User {

    public Admin(String username, String phoneNumber, String email, String password, boolean approved, Role role) {
        super(username, phoneNumber, email, password, approved, role);
    }
}
