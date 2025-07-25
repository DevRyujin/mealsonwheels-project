package com.merrymeal.mealsonwheels.service.mealOrderService;

import com.merrymeal.mealsonwheels.dto.mealDTOs.MealDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderMealDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderRequestDTO;
import com.merrymeal.mealsonwheels.dto.order.RiderOrderDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.*;
import com.merrymeal.mealsonwheels.repository.*;
import com.merrymeal.mealsonwheels.dto.order.MemberOrderDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderSummaryDTO;
import com.merrymeal.mealsonwheels.model.Order;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.model.Role;


import com.merrymeal.mealsonwheels.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private MemberProfileRepository memberProfileRepository;

    @Autowired
    private PartnerProfileRepository partnerProfileRepository;

    @Autowired
    private CaregiverProfileRepository caregiverProfileRepository;

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

    public ResponseEntity<?> assignRider(Long orderId, Long riderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        RiderProfile rider = riderProfileRepository.findById(riderId)
                .orElseThrow(() -> new RuntimeException("Rider not found"));

        order.setRider(rider);
        order.setStatus(TaskStatus.ASSIGNED);
        orderRepository.save(order);

        return ResponseEntity.ok("Rider assigned successfully.");
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
        List<OrderMealDTO> mealDTOs = order.getOrderMeals().stream().map(orderMeal -> {
            Meal meal = orderMeal.getMeal();
            MealDTO mealDTO = MealDTO.builder()
                    .id(meal.getId())
                    .mealName(meal.getMealName())
                    .mealPhoto(meal.getMealPhoto())
                    .mealDesc(meal.getMealDesc())
                    .mealType(meal.getMealType())
                    .mealDietary(meal.getMealDietary())
                    .mealCreatedDate(meal.getMealCreatedDate())
                    .build();

            return new OrderMealDTO(mealDTO, orderMeal.getQuantity());
        }).collect(Collectors.toList());

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

                .riderName(order.getRider() != null ? order.getRider().getUser().getName() : null)
                .riderEmail(order.getRider() != null ? order.getRider().getUser().getEmail() : null)

                .orderMeals(mealDTOs)
                .memberName(order.getMember() != null ? order.getMember().getUser().getName() : null)
                .mealName(mealDTOs.isEmpty() ? null : mealDTOs.get(0).getMealName())
                .restaurant(order.getPartner() != null ? order.getPartner().getUser().getName() : null)
                .restaurantAddress(order.getPartner() != null ? order.getPartner().getUser().getAddress() : null)
                .code(order.getCode())
                .startDeliveryTime(order.getStartDeliveryTime())
                .orderDate(order.getCreatedAt())
                .build();
    }



    public RiderOrderDTO mapToRiderOrderDTO(Order order) {
        return RiderOrderDTO.builder()
                .orderId(order.getId())
                .code("ORD-" + order.getId())
                .orderType(order.getOrderType())
                .status(order.getStatus().name())
                .startDeliveryTime(order.getStartDeliveryTime())
                .endDeliveryTime(order.getEndDeliveryTime())

                .meals(order.getOrderMeals() != null
                        ? order.getOrderMeals().stream().map(this::mapToOrderMealDTO).toList()
                        : List.of())

                .restaurantName(order.getPartner() != null ? order.getPartner().getCompanyName() : null)
                .restaurantAddress(order.getPartner() != null ? order.getPartner().getCompanyAddress() : null)

                .memberName(order.getMember() != null ? order.getMember().getUser().getName() : null)
                .deliveryAddress(order.getMember() != null ? order.getMember().getAddress() : null)
                .contactNumber(order.getMember() != null && order.getMember().getUser() != null
                        ? order.getMember().getUser().getPhone()
                        : null) // FIXED with null check

                .estimatedDistanceKm(null) // You can add logic here later
                .estimatedDeliveryTime(null) // Same here
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


    public List<MemberOrderDTO> getMembersWithOrders() {
        List<User> members = userRepository.findAll().stream()
                .filter(user -> user.getRole().name().equals("MEMBER"))
                .collect(Collectors.toList());

        List<Order> allOrders = orderRepository.findAll();

        Map<Long, List<Order>> ordersByMember = allOrders.stream()
                .filter(order -> order.getMember() != null)
                .collect(Collectors.groupingBy(order -> order.getMember().getUser().getId()));

        List<MemberOrderDTO> result = new ArrayList<>();

        for (User member : members) {
            List<Order> memberOrders = ordersByMember.getOrDefault(member.getId(), List.of());

            List<OrderSummaryDTO> orderSummaries = memberOrders.stream().map(order ->
                    OrderSummaryDTO.builder()
                            .orderId(order.getId())
                            .code(order.getCode())
                            //.orderType(order.getOrderType() != null ? order.getOrderType() : null)
                            .status(order.getStatus() != null ? order.getStatus().name() : null)
                            .restaurantName(order.getPartner() != null ? order.getPartner().getCompanyName() : "N/A")
                            .restaurantAddress(order.getPartner() != null ? order.getPartner().getCompanyAddress() : "N/A")
                            .orderType(order.getOrderType() != null ? order.getOrderType() : "N/A")

                            //.restaurantName(order.getPartner() != null ? order.getPartner().getCompanyName() : null)
                            //.restaurantAddress(order.getPartner() != null ? order.getPartner().getCompanyAddress() : null)
                            .riderName(order.getRider() != null && order.getRider().getUser() != null
                                    ? order.getRider().getUser().getName()
                                    : null)
                            .riderEmail(order.getRider() != null && order.getRider().getUser() != null
                                    ? order.getRider().getUser().getEmail()
                                    : null)
                            .totalAmount(order.getTotalAmount())
                            .createdAt(order.getCreatedAt() != null ? order.getCreatedAt().toString() : null)
                            .build()
            ).toList();

            Order assignedOrder = memberOrders.stream()
                    .filter(o -> o.getRider() != null)
                    .findFirst()
                    .orElse(null);

            MemberOrderDTO dto = MemberOrderDTO.builder()
                    .memberId(member.getId())
                    .memberName(member.getName())
                    .address(member.getAddress())
                    .contactNumber(member.getPhone())
                    .riderName(assignedOrder != null && assignedOrder.getRider() != null && assignedOrder.getRider().getUser() != null
                            ? assignedOrder.getRider().getUser().getName()
                            : null)
                    .riderEmail(assignedOrder != null && assignedOrder.getRider() != null && assignedOrder.getRider().getUser() != null
                            ? assignedOrder.getRider().getUser().getEmail()
                            : null)
                    .orders(orderSummaries)
                    .build();


            result.add(dto);
        }

        return result;
    }

    public List<MemberOrderDTO> getCaregiversWithOrders() {
        List<User> caregivers = userRepository.findAll().stream()
                .filter(user -> user.getRole().name().equals("CAREGIVER"))
                .collect(Collectors.toList());

        List<Order> allOrders = orderRepository.findAll();

        Map<Long, List<Order>> ordersByCaregiver = allOrders.stream()
                .filter(order -> order.getCaregiver() != null)
                .collect(Collectors.groupingBy(order -> order.getCaregiver().getUser().getId()));

        List<MemberOrderDTO> result = new ArrayList<>();

        for (User caregiver : caregivers) {
            List<Order> caregiverOrders = ordersByCaregiver.getOrDefault(caregiver.getId(), List.of());

            List<OrderSummaryDTO> orderSummaries = caregiverOrders.stream().map(order ->
                    OrderSummaryDTO.builder()
                            .orderId(order.getId())
                            .code(order.getCode())
                            .orderType(order.getOrderType() != null ? order.getOrderType() : "N/A")
                            .status(order.getStatus() != null ? order.getStatus().name() : null)
                            .restaurantName(order.getPartner() != null ? order.getPartner().getCompanyName() : "N/A")
                            .restaurantAddress(order.getPartner() != null ? order.getPartner().getCompanyAddress() : "N/A")
                            .riderName(order.getRider() != null && order.getRider().getUser() != null
                                    ? order.getRider().getUser().getName()
                                    : null)
                            .riderEmail(order.getRider() != null && order.getRider().getUser() != null
                                    ? order.getRider().getUser().getEmail()
                                    : null)
                            .totalAmount(order.getTotalAmount())
                            .createdAt(order.getCreatedAt() != null ? order.getCreatedAt().toString() : null)
                            .build()
            ).toList();

            Order assignedOrder = caregiverOrders.stream()
                    .filter(o -> o.getRider() != null)
                    .findFirst()
                    .orElse(null);

            MemberOrderDTO dto = MemberOrderDTO.builder()
                    .caregiverId(caregiver.getId())
                    .caregiverName(caregiver.getName())
                    .caregiverAddress(caregiver.getAddress())
                    .caregiverContactNumber(caregiver.getPhone())
                    .assistedMemberName(caregiver.getCaregiverProfile() != null
                            ? caregiver.getCaregiverProfile().getMemberNameToAssist()
                            : null)
                    .assistedMemberAddress(caregiver.getCaregiverProfile() != null
                            ? caregiver.getCaregiverProfile().getMemberAddressToAssist()
                            : null)
                    .riderName(assignedOrder != null && assignedOrder.getRider() != null && assignedOrder.getRider().getUser() != null
                            ? assignedOrder.getRider().getUser().getName()
                            : null)
                    .riderEmail(assignedOrder != null && assignedOrder.getRider() != null && assignedOrder.getRider().getUser() != null
                            ? assignedOrder.getRider().getUser().getEmail()
                            : null)
                    .orders(orderSummaries)
                    .build();

            result.add(dto);
        }

        return result;
    }



    // For Member only
    public void placeOrder(OrderRequestDTO dto) {
        // Fetch profiles by user ID
        MemberProfile member = memberProfileRepository.findByUserId(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        PartnerProfile partner = partnerProfileRepository.findByUserId(dto.getPartnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

        // Validate coordinates
        Double memberLat = member.getUser().getLatitude();
        Double memberLong = member.getUser().getLongitude();

        Double partnerLat = partner.getUser().getLatitude();
        Double partnerLong = partner.getUser().getLongitude();

        if (memberLat == null || memberLong == null) {
            throw new RuntimeException("❌ Member location is missing");
        }

        if (partnerLat == null || partnerLong == null) {
            throw new RuntimeException("❌ Partner location is missing");
        }

        double distance = DistanceUtil.calculateDistanceKm(
                memberLat, memberLong,
                partnerLat, partnerLong
        );

        System.out.printf("📍 Distance between member and partner: %.2f km%n", distance);

        // Determine meal type
        String orderType;
        if (distance == 0.0) {
            orderType = "HOT"; // No need to offer Frozen if same location
        } else {
            orderType = distance <= 10.0 ? "HOT" : "FROZEN";
        }

        System.out.println("🔥 Order Type Assigned: " + orderType);

        // Build Order entity
        Order order = Order.builder()
                .totalAmount(dto.getTotalAmount())
                .member(member)
                .partner(partner)
                .orderType(orderType)
                .status(TaskStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .orderMeals(new ArrayList<>())
                .build();

        // Add rider if exists
        if (dto.getRiderId() != null) {
            RiderProfile rider = riderProfileRepository.findByUserId(dto.getRiderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rider not found"));
            order.setRider(rider);
        }

        // Add meals (if provided)
        if (dto.getMeals() != null && !dto.getMeals().isEmpty()) {
            for (OrderMealDTO mealDTO : dto.getMeals()) {
                Meal meal = mealRepository.findById(mealDTO.getMealId())
                        .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + mealDTO.getMealId()));

                OrderMeal orderMeal = new OrderMeal();
                orderMeal.setMeal(meal);
                orderMeal.setQuantity(mealDTO.getQuantity() != null ? mealDTO.getQuantity() : 1);
                order.addOrderMeal(orderMeal);
            }
        }

        // Save order
        orderRepository.save(order);
    }

    // For Caregiver only
    public void placeOrderForCaregiver(OrderRequestDTO dto) {

        CaregiverProfile caregiver = caregiverProfileRepository.findByUserId(dto.getCaregiverId())
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));

        PartnerProfile partner = partnerProfileRepository.findByUserId(dto.getPartnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));


        // ✅ Caregiver's location (assumed saved in caregiver profile)
        Double caregiverLat = caregiver.getUser().getLatitude();
        Double caregiverLong = caregiver.getUser().getLongitude();

        // ✅ Partner location should come from frontend (add if not in DTO)
        Double partnerLat = partner.getUser().getLatitude();
        Double partnerLong = partner.getUser().getLongitude();

        if (caregiverLat == null || caregiverLong == null) {
            throw new RuntimeException("❌ Member location is missing");
        }

        if (partnerLat == null || partnerLong == null) {
            throw new RuntimeException("❌ Partner location is missing");
        }

        double distance = DistanceUtil.calculateDistanceKm(
                caregiverLat, caregiverLong,
                partnerLat, partnerLong
        );

        System.out.printf("📍 Distance between member and partner: %.2f km%n", distance);

        // Determine meal type
        String orderType;
        if (distance == 0.0) {
            orderType = "HOT"; // No need to offer Frozen if same location
        } else {
            orderType = distance <= 10.0 ? "HOT" : "FROZEN";
        }

        System.out.println("🔥 Order Type Assigned: " + orderType);

        // Build Order entity
        Order order = Order.builder()
                .totalAmount(dto.getTotalAmount())
                .caregiver(caregiver)
                .recipientName(caregiver.getMemberNameToAssist())      // ✅ save caregiver’s registered member name
                .deliveryAddress(caregiver.getMemberAddressToAssist())
                .partner(partner)
                .orderType(orderType)
                .status(TaskStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .orderMeals(new ArrayList<>())
                .build();

        // Add rider if exists
        if (dto.getRiderId() != null) {
            RiderProfile rider = riderProfileRepository.findByUserId(dto.getRiderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rider not found"));
            order.setRider(rider);
        }

        // Add meals (if provided)
        if (dto.getMeals() != null && !dto.getMeals().isEmpty()) {
            for (OrderMealDTO mealDTO : dto.getMeals()) {
                Meal meal = mealRepository.findById(mealDTO.getMealId())
                        .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + mealDTO.getMealId()));

                OrderMeal orderMeal = new OrderMeal();
                orderMeal.setMeal(meal);
                orderMeal.setQuantity(mealDTO.getQuantity() != null ? mealDTO.getQuantity() : 1);
                order.addOrderMeal(orderMeal);
            }
        }

        // Save order
        orderRepository.save(order);
    }

    public List<MemberOrderDTO> getMembersOrderHistory() {
        List<User> members = userRepository.findAll().stream()
                .filter(user -> user.getRole().name().equals("MEMBER"))
                .collect(Collectors.toList());

        List<Order> orderedMeals = orderRepository.findAll();

        Map<Long, List<Order>> ordersOfMember = orderedMeals.stream()
                .filter(order -> order.getMember() != null)
                .collect(Collectors.groupingBy(order -> order.getMember().getUser().getId()));

        List<MemberOrderDTO> orderHistory = new ArrayList<>();

        for (User member : members) {
            List<Order> memberHistory = ordersOfMember.getOrDefault(member.getId(), List.of());

            List<OrderSummaryDTO> orderSummaries = memberHistory.stream().map(order ->
                    OrderSummaryDTO.builder()
                            .orderId(order.getId())
                            .restaurantName(order.getPartner() != null ? order.getPartner().getCompanyName() : "N/A")
                            .restaurantAddress(order.getPartner() != null ? order.getPartner().getCompanyAddress() : "N/A")
                            .riderName(order.getRider() != null && order.getRider().getUser() != null
                                    ? order.getRider().getUser().getName()
                                    : null)
                            .riderEmail(order.getRider() != null && order.getRider().getUser() != null
                                    ? order.getRider().getUser().getEmail()
                                    : null)
                            .totalAmount(order.getTotalAmount())
                            .createdAt(order.getCreatedAt() != null ? order.getCreatedAt().toString() : null)
                            .build()
            ).toList();

            Order assignedOrder = memberHistory.stream()
                    .filter(o -> o.getRider() != null)
                    .findFirst()
                    .orElse(null);

            MemberOrderDTO dto = MemberOrderDTO.builder()
                    .memberId(member.getId())
                    .memberName(member.getName())
                    .address(member.getAddress())
                    .contactNumber(member.getPhone())
                    .riderName(assignedOrder != null && assignedOrder.getRider() != null && assignedOrder.getRider().getUser() != null
                            ? assignedOrder.getRider().getUser().getName()
                            : null)
                    .riderEmail(assignedOrder != null && assignedOrder.getRider() != null && assignedOrder.getRider().getUser() != null
                            ? assignedOrder.getRider().getUser().getEmail()
                            : null)
                    .orders(orderSummaries)
                    .build();

            orderHistory.add(dto);

        }

        return orderHistory;
    }


}
