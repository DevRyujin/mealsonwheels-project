package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;

public interface MemberService {
    MemberProfileDTO getMemberProfile();
    MemberProfileDTO updateMemberProfile(MemberProfileDTO updatedInfo);
    CaregiverProfileDTO getAssignedCaregiver();
}
