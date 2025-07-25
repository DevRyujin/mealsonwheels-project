package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.order.OrderDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderMealDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.RiderProfileDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.*;
        import com.merrymeal.mealsonwheels.repository.OrderRepository;
import com.merrymeal.mealsonwheels.repository.RiderProfileRepository;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final RiderProfileRepository riderProfileRepository;
    private final OrderRepository orderRepository;

    public List<OrderDTO> getPendingOrders() {
        try {
            RiderProfile rider = getCurrentRider();
            return orderRepository.findByRider(rider).stream()
                    .filter(o -> o.getStatus() != null && o.getStatus() != TaskStatus.COMPLETED)
                    .map(this::mapToOrderDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace(); // debug line
            throw e;
        }
    }

    @Override
    public List<OrderDTO> getDeliveredOrders() {
        RiderProfile rider = getCurrentRider();
        return orderRepository.findByRider(rider).stream()
                .filter(o -> TaskStatus.COMPLETED.equals(o.getStatus()))
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

                // Extra fields for frontend:
                .memberName(order.getMember() != null ? order.getMember().getUser().getName() : null)
                .mealName(order.getOrderMeals().isEmpty() ? null : order.getOrderMeals().get(0).getMeal().getMealName())
                .memberAddress(
                        order.getMember() != null && order.getMember().getUser() != null
                                ? order.getMember().getUser().getAddress()
                                : null
                )

                .restaurant(order.getPartner() != null ? order.getPartner().getCompanyName() : null)
                .restaurantAddress(order.getPartner() != null ? order.getPartner().getCompanyAddress() : null) // FIXED
                //.partnerAddress(order.getPartner() != null ? order.getPartner().getCompanyAddress() : null)
                .code("ORD-" + order.getId())
                .startDeliveryTime(order.getStartDeliveryTime())

                .orderMeals(order.getOrderMeals().stream()
                        .map(this::mapToOrderMealDTO)
                        .collect(Collectors.toList()))
                .build();
    }



    private OrderMealDTO mapToOrderMealDTO(OrderMeal orderMeal) {
        Meal meal = orderMeal.getMeal();
        return OrderMealDTO.builder()
                .mealId(meal.getId())
                .mealName(meal.getMealName())
                .mealPhoto(meal.getMealPhoto())
                .mealDesc(meal.getMealDesc())
                .mealType(meal.getMealType().name())
                .mealDietary(meal.getMealDietary())
                .mealCreatedDate(meal.getMealCreatedDate())
                .quantity(orderMeal.getQuantity())
                .build();
    }

    @Override
    public RiderProfileDTO getCurrentRiderProfileDTO() {
        RiderProfile rider = getCurrentRider();
        return toRiderProfileDTO(rider);
    }

    @Override
    @Transactional
    public void startDelivery(Long orderId) {
        RiderProfile rider = getCurrentRider();
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getRider() != null && o.getRider().getId().equals(rider.getId()))
                .orElseThrow(() -> new RuntimeException("Order not found or not assigned to this rider"));

        if (order.getStatus() != TaskStatus.ASSIGNED) {
            throw new RuntimeException("Order must be ASSIGNED before it can be started.");
        }

        order.setStatus(TaskStatus.IN_PROGRESS);
        order.setStartDeliveryTime(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void completeDelivery(Long orderId) {
        RiderProfile rider = getCurrentRider();
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getRider() != null && o.getRider().getId().equals(rider.getId()))
                .orElseThrow(() -> new RuntimeException("Order not found or not assigned to this rider"));

        if (order.getStatus() != TaskStatus.IN_PROGRESS) {
            throw new RuntimeException("Order is not currently in progress");
        }

        order.setStatus(TaskStatus.COMPLETED);
        order.setEndDeliveryTime(LocalDateTime.now());
        orderRepository.save(order);
    }


    @Override
    public List<RiderProfileDTO> getApprovedRiders() {
        List<RiderProfile> allRiders = riderProfileRepository.findAll();

        System.out.println("Total riders: " + allRiders.size());

        List<RiderProfileDTO> approved = allRiders.stream()
                .filter(r -> {
                    boolean valid = r.getUser() != null && r.getUser().isApproved();
                    if (!valid) {
                        System.out.println("Skipped rider with ID: " + r.getId());
                    }
                    return valid;
                })
                .map(this::toRiderProfileDTO)
                .collect(Collectors.toList());

        System.out.println("Approved riders returned: " + approved.size());
        return approved;
    }

    public RiderProfileDTO toRiderProfileDTO(RiderProfile rider) {
        if (rider == null) {
            return null;
        }

        User user = rider.getUser();
        PartnerProfile partner = rider.getPartner();

        return RiderProfileDTO.builder()
                .id(rider.getId())
                .name(user != null ? user.getName() : "")
                .email(user != null ? user.getEmail() : "")
                .phone(user != null ? user.getPhone() : "")
                .address(user != null ? user.getAddress() : "")
                .latitude(user != null && user.getLatitude() != null ? user.getLatitude() : 0.0)
                .longitude(user != null && user.getLongitude() != null ? user.getLongitude() : 0.0)
                .approved(user != null && user.isApproved())
                .driverLicenseNumber(rider.getDriverLicenseNumber())
                .licenseExpiryDate(rider.getLicenseExpiryDate())
                .partnerId(partner != null ? partner.getId() : null)
                .partnerCompanyName(partner != null ? partner.getCompanyName() : null)
                .build();
    }

    @Override
    public void acceptDelivery(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() != TaskStatus.ASSIGNED) {
            throw new IllegalStateException("Order is not in ASSIGNED status.");
        }

        order.setStatus(TaskStatus.IN_PROGRESS); // move from ASSIGNED to IN_PROGRESS
        order.setStartDeliveryTime(LocalDateTime.now());
        orderRepository.save(order);
    }





}
