package com.merrymeal.mealsonwheels.dto;

import com.merrymeal.mealsonwheels.model.Role;

public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;

    private Double latitude;
    private Double longitude;

    private Role role;

    private MemberProfileDTO memberProfileDTO;
    private CaregiverProfileDTO caregiverProfileDTO;
    private PartnerProfileDTO partnerProfileDTO;
    private VolunteerProfileDTO volunteerProfileDTO;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public MemberProfileDTO getMemberProfileDTO() {
        return memberProfileDTO;
    }

    public void setMemberProfileDTO(MemberProfileDTO memberProfileDTO) {
        this.memberProfileDTO = memberProfileDTO;
    }

    public CaregiverProfileDTO getCaregiverProfileDTO() {
        return caregiverProfileDTO;
    }

    public void setCaregiverProfileDTO(CaregiverProfileDTO caregiverProfileDTO) {
        this.caregiverProfileDTO = caregiverProfileDTO;
    }

    public PartnerProfileDTO getPartnerProfileDTO() {
        return partnerProfileDTO;
    }

    public void setPartnerProfileDTO(PartnerProfileDTO partnerProfileDTO) {
        this.partnerProfileDTO = partnerProfileDTO;
    }

    public VolunteerProfileDTO getVolunteerProfileDTO() {
        return volunteerProfileDTO;
    }

    public void setVolunteerProfileDTO(VolunteerProfileDTO volunteerProfileDTO) {
        this.volunteerProfileDTO = volunteerProfileDTO;
    }
}
