package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "donor")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Donor extends User {

    private String donorType;

    private BigDecimal totalDonatedAmount = BigDecimal.ZERO;

    public Donor(String username, String phoneNumber, String email, String password,
                 boolean approved, Role role, String donorType, BigDecimal totalDonatedAmount) {
        super(username, phoneNumber, email, password, approved, role);
        this.donorType = donorType;
        this.totalDonatedAmount = totalDonatedAmount;
    }

    public void addDonation(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            this.totalDonatedAmount = this.totalDonatedAmount.add(amount);
        }
    }
}
