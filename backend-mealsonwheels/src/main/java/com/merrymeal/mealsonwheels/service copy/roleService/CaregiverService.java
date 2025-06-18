package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.CaregiverDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.MemberDTO;

import java.util.List;

public interface CaregiverService {
    CaregiverDTO getCurrentCaregiverProfile(Long caregiverId);
    CaregiverDTO updateCurrentCaregiverProfile(Long caregiverId, CaregiverDTO updatedInfo);
    List<MemberDTO> getMembersUnderCare(Long caregiverId);
    MemberDTO assignMemberToCurrentCaregiver(Long memberId);
}
