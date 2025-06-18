package com.merrymeal.mealsonwheels_backend.service.mealOrderService;

import com.merrymeal.mealsonwheels_backend.dto.OrderRequestDTO;
import com.merrymeal.mealsonwheels_backend.dto.OrderMealDTO;
import com.merrymeal.mealsonwheels_backend.dto.OrderDTO;
import com.merrymeal.mealsonwheels_backend.model.*;
import com.merrymeal.mealsonwheels_backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRangeService deliveryRangeService;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    public void placeOrder(OrderRequestDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Partner partner = partnerRepository.findById(dto.getPartnerId())
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        boolean withinRange = deliveryRangeService.isWithinDeliveryRange(
                member.getMemberLocationLat(), member.getMemberLocationLong(),
                partner.getCompanyLocationLat(), partner.getCompanyLocationLong());

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
            Rider rider = riderRepository.findById(dto.getRiderId())
                    .orElseThrow(() -> new RuntimeException("Rider not found"));
            order.setRider(rider);
        }

        if (dto.getMeals() != null) {
            for (OrderMealDTO mealDTO : dto.getMeals()) {
                Meal meal = mealRepository.findById(mealDTO.getMealId())
                        .orElseThrow(() -> new RuntimeException("Meal not found with ID: " + mealDTO.getMealId()));

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
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(() -> new RuntimeException("Rider not found"));

        order.setRider(rider);
        order.setStatus("Assigned");
        orderRepository.save(order);
    }

    public Order getOrderByIdIfAllowed(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        boolean isMember = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_MEMBER"));

        if (isAdmin) {
            return order;
        }

        if (isMember) {
            if (order.getMember() != null && order.getMember().getId().equals(currentUser.getId())) {
                return order;
            } else {
                throw new AccessDeniedException("You are not allowed to view this order.");
            }
        }

        throw new AccessDeniedException("Access denied.");
    }

    // ✅ NEW: Get all orders assigned to currently authenticated rider
    public List<OrderDTO> getOrdersForCurrentRider() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Rider rider = riderRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Rider not found"));

        return orderRepository.findByRider(rider)
                .stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    // ✅ NEW: Convert Order -> OrderDTO
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
                .partnerName(order.getPartner() != null ? order.getPartner().getUsername() : null)
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
