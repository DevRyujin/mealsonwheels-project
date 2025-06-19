package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels_backend.dto.OrderDTO;

import java.util.List;

public interface RiderService {
    List<OrderDTO> getPendingOrders();
    List<OrderDTO> getDeliveredOrders();
    OrderDTO getOrderDetails(Long orderId);
}
