package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.order.OrderDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.RiderProfileDTO;
import com.merrymeal.mealsonwheels.model.RiderProfile;

import java.util.List;

public interface RiderService {
    List<OrderDTO> getPendingOrders();
    List<OrderDTO> getDeliveredOrders();
    OrderDTO getOrderDetails(Long orderId);
    RiderProfileDTO toRiderProfileDTO(RiderProfile rider);
    RiderProfileDTO getCurrentRiderProfileDTO();
    void startDelivery(Long orderId);
    void completeDelivery(Long orderId);

    List<RiderProfileDTO> getApprovedRiders();

    void acceptDelivery(Long id);
}
