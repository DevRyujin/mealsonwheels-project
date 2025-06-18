package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels_backend.dto.OrderMealDTO;
import com.merrymeal.mealsonwheels_backend.dto.OrderDTO;
import com.merrymeal.mealsonwheels_backend.model.Order;
import com.merrymeal.mealsonwheels_backend.model.OrderMeal;
import com.merrymeal.mealsonwheels_backend.model.Rider;
import com.merrymeal.mealsonwheels_backend.repository.OrderRepository;
import com.merrymeal.mealsonwheels_backend.repository.RiderRepository;
import com.merrymeal.mealsonwheels_backend.security.SecurityUtil;
import com.merrymeal.mealsonwheels_backend.util.UserValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final RiderRepository riderRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<OrderDTO> getPendingOrders() {
        Rider rider = getCurrentRider();
        return orderRepository.findByRiderAndStatusNot(rider, "Delivered").stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getDeliveredOrders() {
        Rider rider = getCurrentRider();
        return orderRepository.findByRiderAndStatus(rider, "Delivered").stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderDetails(Long orderId) {
        Rider rider = getCurrentRider();
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getRider() != null && o.getRider().equals(rider))
                .orElseThrow(() -> new RuntimeException("Order not found or not assigned to you"));

        return mapToOrderDTO(order);
    }

    private Rider getCurrentRider() {
        Long userId = SecurityUtil.getCurrentUserId();
        Rider rider = riderRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Rider not found with ID: " + userId));

        UserValidationUtil.checkApproved(rider);
        UserValidationUtil.checkRole(rider, "RIDER");

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
                .memberUsername(order.getMember() != null ? order.getMember().getUsername() : null)
                .partnerId(order.getPartner() != null ? order.getPartner().getId() : null)
                .partnerName(order.getPartner() != null ? order.getPartner().getCompanyName() : null)
                .riderId(order.getRider() != null ? order.getRider().getId() : null)
                .orderMeals(order.getOrderMeals().stream().map(this::mapToOrderMealDTO).collect(Collectors.toList()))
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
