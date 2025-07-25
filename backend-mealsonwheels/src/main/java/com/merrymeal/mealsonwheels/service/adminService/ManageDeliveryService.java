package com.merrymeal.mealsonwheels.service.adminService;

import com.merrymeal.mealsonwheels.dto.order.MemberOrderDTO;

import java.util.List;

public interface ManageDeliveryService {
    List<MemberOrderDTO> getMembersWithOrders();
    void assignRider(Long orderId, Long riderId);
}
