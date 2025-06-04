package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;

@Entity
@Table(name = "partner_profiles")
public class PartnerProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String companyName;
    private String partnershipDuration;

    public PartnerProfile() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPartnershipDuration() {
        return partnershipDuration;
    }

    public void setPartnershipDuration(String partnershipDuration) {
        this.partnershipDuration = partnershipDuration;
    }
}
