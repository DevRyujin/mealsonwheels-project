package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "rider")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Rider extends User {

    private String driverLicenseNumber;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL)
    private List<Order> deliveredOrders;

    public Rider(String username, String phoneNumber, String email, String password,
            boolean approved, Role role, String driverLicenseNumber) {
        super(username, phoneNumber, email, password, approved, role);
        this.driverLicenseNumber = driverLicenseNumber;
    }
}
