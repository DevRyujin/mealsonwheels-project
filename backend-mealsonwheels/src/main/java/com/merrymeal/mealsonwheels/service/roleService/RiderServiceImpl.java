package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.order.OrderDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderMealDTO;
import com.merrymeal.mealsonwheels.model.*;
import com.merrymeal.mealsonwheels.repository.OrderRepository;
import com.merrymeal.mealsonwheels.repository.RiderProfileRepository;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final RiderProfileRepository riderProfileRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<OrderDTO> getPendingOrders() {
        RiderProfile rider = getCurrentRider();
        return orderRepository.findByRider(rider).stream()
                .filter(o -> !"Delivered".equals(o.getStatus()))
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getDeliveredOrders() {
        RiderProfile rider = getCurrentRider();
        return orderRepository.findByRider(rider).stream()
                .filter(o -> "Delivered".equals(o.getStatus()))
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderDetails(Long orderId) {
        RiderProfile rider = getCurrentRider();
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getRider() != null && o.getRider().getId().equals(rider.getId()))
                .orElseThrow(() -> new RuntimeException("Order not found or not assigned to this rider"));

        return mapToOrderDTO(order);
    }

    private RiderProfile getCurrentRider() {
        Long userId = SecurityUtil.getCurrentUserId();
        RiderProfile rider = riderProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Rider profile not found for user ID: " + userId));

        User user = rider.getUser();
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, Role.RIDER);

        return rider;
    }

    private OrderDTO mapToOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .orderType(order.getOrderType())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .memberId(order.getMember() != null ? order.getMember().getId() : null)
                .memberUsername(order.getMember() != null ? order.getMember().getUser().getName() : null)
                .partnerId(order.getPartner() != null ? order.getPartner().getId() : null)
                .partnerName(order.getPartner() != null ? order.getPartner().getCompanyName() : null)
                .riderId(order.getRider() != null ? order.getRider().getId() : null)
                .orderMeals(order.getOrderMeals().stream()
                        .map(this::mapToOrderMealDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private OrderMealDTO mapToOrderMealDTO(OrderMeal orderMeal) {
        return OrderMealDTO.builder()
                .mealId(orderMeal.getMeal().getId())
                .mealName(orderMeal.getMeal().getMealName())
                .quantity(orderMeal.getQuantity())
                .build();
    }
}
