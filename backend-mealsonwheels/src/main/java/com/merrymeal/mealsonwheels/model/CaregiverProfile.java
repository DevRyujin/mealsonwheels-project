package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;

@Entity
@Table(name = "caregiver_profiles")
public class CaregiverProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "assigned_member_id")
    private User assignedMember;

    private String memberNameToAssist;
    private String memberPhoneNumberToAssist;
    private String memberAddressToAssist;
    private String memberRelationship;


    public CaregiverProfile() {

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

    public User getAssignedMember() {
        return assignedMember;
    }

    public void setAssignedMember(User assignedMember) {
        this.assignedMember = assignedMember;
    }

    public String getMemberNameToAssist() {
        return memberNameToAssist;
    }

    public void setMemberNameToAssist(String memberNameToAssist) {
        this.memberNameToAssist = memberNameToAssist;
    }

    public String getMemberPhoneNumberToAssist() {
        return memberPhoneNumberToAssist;
    }

    public void setMemberPhoneNumberToAssist(String memberPhoneNumberToAssist) {
        this.memberPhoneNumberToAssist = memberPhoneNumberToAssist;
    }

    public String getMemberAddressToAssist() {
        return memberAddressToAssist;
    }

    public void setMemberAddressToAssist(String memberAddressToAssist) {
        this.memberAddressToAssist = memberAddressToAssist;
    }

    public String getMemberRelationship() {
        return memberRelationship;
    }

    public void setMemberRelationship(String memberRelationship) {
        this.memberRelationship = memberRelationship;
    }
}
