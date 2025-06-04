package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    // for Geolocation
    private String address;
    private Double latitude;
    private Double longitude;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private MemberProfile memberProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CaregiverProfile caregiverProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private VolunteerProfile volunteerProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PartnerProfile partnerProfile;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public MemberProfile getMemberProfile() {
        return memberProfile;
    }

    public void setMemberProfile(MemberProfile memberProfile) {
        this.memberProfile = memberProfile;
    }

    public CaregiverProfile getCaregiverProfile() {
        return caregiverProfile;
    }

    public void setCaregiverProfile(CaregiverProfile caregiverProfile) {
        this.caregiverProfile = caregiverProfile;
    }

    public VolunteerProfile getVolunteerProfile() {
        return volunteerProfile;
    }

    public void setVolunteerProfile(VolunteerProfile volunteerProfile) {
        this.volunteerProfile = volunteerProfile;
    }

    public PartnerProfile getPartnerProfile() {
        return partnerProfile;
    }

    public void setPartnerProfile(PartnerProfile partnerProfile) {
        this.partnerProfile = partnerProfile;
    }
}
