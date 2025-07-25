package com.merrymeal.mealsonwheels.service.roleService;


import com.merrymeal.mealsonwheels.dto.order.OrderDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderRequestDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;

import java.util.List;

public interface CaregiverService {
    // Should match this
    CaregiverProfileDTO getCurrentCaregiverProfile(Long caregiverId);

    CaregiverProfileDTO updateCurrentCaregiverProfile(Long caregiverId, CaregiverProfileDTO updatedInfo);

    List<MemberProfileDTO> getMembersUnderCare(Long caregiverId);

    MemberProfileDTO assignMemberToCurrentCaregiver(Long memberId);

    //void placeOrderForCaregiver(OrderRequestDTO dto);

    //void placeOrderForCaregiver(Long caregiverId, OrderDTO orderDTO);

}
