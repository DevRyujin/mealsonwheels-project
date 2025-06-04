package com.merrymeal.mealsonwheels.dto;

public class CaregiverProfileDTO {
    private String assignedMember;
    private String memberNameToAssist;
    private String memberPhoneNumberToAssist;
    private String memberAddressToAssist;
    private String memberRelationship;

    public CaregiverProfileDTO() {

    }

    public String getAssignedMember() {
        return assignedMember;
    }

    public void setAssignedMember(String assignedMember) {
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
