package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberDTO;

import java.util.List;

public interface CaregiverService {
    CaregiverDTO getCurrentCaregiverProfile(Long caregiverId);
    CaregiverDTO updateCurrentCaregiverProfile(Long caregiverId, CaregiverDTO updatedInfo);
    List<MemberDTO> getMembersUnderCare(Long caregiverId);
    MemberDTO assignMemberToCurrentCaregiver(Long memberId);
}
