package com.merrymeal.mealsonwheels.service.mealOrderService;

import com.merrymeal.mealsonwheels.dto.order.OrderDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderMealDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderRequestDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.*;
import com.merrymeal.mealsonwheels.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private MemberProfileRepository memberProfileRepository;

    @Autowired
    private PartnerProfileRepository partnerProfileRepository;

    @Autowired
    private RiderProfileRepository riderProfileRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRangeService deliveryRangeService;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }


    public void placeOrder(OrderRequestDTO dto) {
        MemberProfile member = memberProfileRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        PartnerProfile partner = partnerProfileRepository.findById(dto.getPartnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

        boolean withinRange = deliveryRangeService.isWithinDeliveryRange(
                member.getMemberLocationLat(), member.getMemberLocationLong(),
                partner.getCompanyLocationLat(), partner.getCompanyLocationLong()
        );

        String orderType = withinRange ? "HOT" : "FROZEN";

        Order order = Order.builder()
                .totalAmount(dto.getTotalAmount())
                .member(member)
                .partner(partner)
                .orderType(orderType)
                .status("Pending")
                .createdAt(LocalDateTime.now())
                .build();

        if (dto.getRiderId() != null) {
            RiderProfile rider = riderProfileRepository.findById(dto.getRiderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rider not found"));
            order.setRider(rider);
        }

        if (dto.getMeals() != null) {
            for (OrderMealDTO mealDTO : dto.getMeals()) {
                Meal meal = mealRepository.findById(mealDTO.getMealId())
                        .orElseThrow(() -> new ResourceNotFoundException("Meal not found"));
                OrderMeal orderMeal = new OrderMeal();
                orderMeal.setMeal(meal);
                orderMeal.setQuantity(mealDTO.getQuantity() != null ? mealDTO.getQuantity() : 1);
                order.addOrderMeal(orderMeal);
            }
        }

        orderRepository.save(order);
    }

    public void assignRider(Long orderId, Long riderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        RiderProfile rider = riderProfileRepository.findById(riderId)
                .orElseThrow(() -> new ResourceNotFoundException("Rider not found"));
        order.setRider(rider);
        order.setStatus("Assigned");
        orderRepository.save(order);
    }

    public Order getOrderByIdIfAllowed(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(username)  // recently add after user repo update
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isAdmin = currentUser.getRole().name().equals("ADMIN");
        boolean isMember = currentUser.getRole().name().equals("MEMBER");

        if (isAdmin) return order;

        if (isMember && order.getMember() != null &&
                order.getMember().getUser().getId().equals(currentUser.getId())) {
            return order;
        }

        throw new AccessDeniedException("Access denied.");
    }

    public List<OrderDTO> getOrdersForCurrentRider() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        RiderProfile rider = riderProfileRepository.findByUser_Email(username) // Recently added after fix on user repo and rider repo
                .orElseThrow(() -> new ResourceNotFoundException("Rider not found"));

        return orderRepository.findByRider(rider)
                .stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
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
                .partnerName(order.getPartner() != null ? order.getPartner().getUser().getName() : null)
                .riderId(order.getRider() != null ? order.getRider().getId() : null)
                .orderMeals(order.getOrderMeals().stream().map(orderMeal ->
                        OrderMealDTO.builder()
                                .mealId(orderMeal.getMeal().getId())
                                .quantity(orderMeal.getQuantity())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
