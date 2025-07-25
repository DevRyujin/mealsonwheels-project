package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.order.OrderDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.model.Order;

import java.util.List;

public interface MemberService {
    MemberProfileDTO getMemberProfile();
    MemberProfileDTO updateMemberProfile(MemberProfileDTO updatedInfo);
    CaregiverProfileDTO getAssignedCaregiver();

    List<OrderDTO> getPendingOrders();
    List<OrderDTO> getDeliveredOrders();

    List<OrderDTO> getCaregiverPendingOrders();

    List<OrderDTO> getCaregiverDeliveredOrders();
}
