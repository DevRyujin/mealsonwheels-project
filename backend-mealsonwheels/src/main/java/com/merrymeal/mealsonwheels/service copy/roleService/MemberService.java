package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.CaregiverDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.MemberDTO;

public interface MemberService {
    // Get the profile of the currently authenticated member
   MemberDTO getMemberProfile();
    MemberDTO updateMemberProfile(MemberDTO updatedInfo);
    CaregiverDTO getAssignedCaregiver();
}
