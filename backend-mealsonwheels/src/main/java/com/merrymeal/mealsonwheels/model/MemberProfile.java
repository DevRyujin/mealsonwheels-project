package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;

@Entity
@Table(name = "member_profiles")
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String dietaryRestrictions;

    // Add caregiver relationship (linked by User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id")
    private CaregiverProfile caregiver;

    // Add geolocation and address fields
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double memberLocationLat;

    @Column(nullable = false)
    private double memberLocationLong;

    @Column(nullable = false)
    private boolean approved = false;

    public MemberProfile() {}

    // Getters and setters
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

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public CaregiverProfile getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(CaregiverProfile caregiver) {
        this.caregiver = caregiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMemberLocationLat() {
        return memberLocationLat;
    }

    public void setMemberLocationLat(double memberLocationLat) {
        this.memberLocationLat = memberLocationLat;
    }

    public double getMemberLocationLong() {
        return memberLocationLong;
    }

    public void setMemberLocationLong(double memberLocationLong) {
        this.memberLocationLong = memberLocationLong;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
