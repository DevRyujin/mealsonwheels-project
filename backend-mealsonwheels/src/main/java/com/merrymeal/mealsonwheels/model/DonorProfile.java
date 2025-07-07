package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String donorName;
    private String contactInfo;
    private String donorType;

    private String cardHolderName;
    private String cardType;
    private String cardNumberMasked;
    private Integer expiryMonth;
    private Integer expiryYear;

    private LocalDateTime lastDonationDate;

    @Builder.Default
    private BigDecimal totalDonatedAmount = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addDonation(BigDecimal amount) {
        if (amount != null) {
            this.totalDonatedAmount = this.totalDonatedAmount.add(amount);
            this.lastDonationDate = LocalDateTime.now(); // accurate timestamp
        }
    }
}
