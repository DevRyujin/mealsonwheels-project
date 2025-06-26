package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.order.OrderDTO;

import java.util.List;

public interface RiderService {
    List<OrderDTO> getPendingOrders();
    List<OrderDTO> getDeliveredOrders();
    OrderDTO getOrderDetails(Long orderId);
}
