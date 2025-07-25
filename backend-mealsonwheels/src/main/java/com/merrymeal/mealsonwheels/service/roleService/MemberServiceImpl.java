package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.order.OrderDTO;
import com.merrymeal.mealsonwheels.dto.order.OrderMealDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.model.*;
import com.merrymeal.mealsonwheels.repository.MemberProfileRepository;
import com.merrymeal.mealsonwheels.repository.OrderRepository;
import com.merrymeal.mealsonwheels.repository.ReassessmentEvaluationRepository;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberProfileRepository memberProfileRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReassessmentEvaluationRepository evaluationRepository;


    @Override
    public MemberProfileDTO getMemberProfile() {
        MemberProfile profile = getCurrentMemberProfile();
        return mapToMemberDTO(profile);
    }

    @Override
    public MemberProfileDTO updateMemberProfile(MemberProfileDTO updatedInfo) {
        MemberProfile profile = getCurrentMemberProfile();

        User user = profile.getUser();
        user.setName(updatedInfo.getName());
        user.setEmail(updatedInfo.getEmail());
        user.setPhone(updatedInfo.getPhone());

        profile.setDietaryRestrictions(updatedInfo.getDietaryRestrictions());
        profile.setAddress(updatedInfo.getAddress());
        profile.setMemberLocationLat(updatedInfo.getMemberLocationLat());
        profile.setMemberLocationLong(updatedInfo.getMemberLocationLong());

        return mapToMemberDTO(memberProfileRepository.save(profile));
    }

    @Override
    public CaregiverProfileDTO getAssignedCaregiver() {
        MemberProfile member = getCurrentMemberProfile();
        CaregiverProfile caregiver = member.getCaregiver();

        if (caregiver == null) {
            throw new RuntimeException("No caregiver assigned to this member.");
        }

        return mapToCaregiverDTO(caregiver);
    }

    private MemberProfile getCurrentMemberProfile() {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, Role.MEMBER);

        MemberProfile member = user.getMemberProfile();
        if (member == null) {
            throw new RuntimeException("Member profile not found.");
        }

        return member;
    }

    private MemberProfileDTO mapToMemberDTO(MemberProfile member) {
        User user = member.getUser();

        return MemberProfileDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .approved(user.isApproved())
                .dietaryRestrictions(member.getDietaryRestrictions())
                .address(member.getAddress())
                .memberLocationLat(member.getMemberLocationLat())
                .memberLocationLong(member.getMemberLocationLong())
                .caregiverId(member.getCaregiver() != null ? member.getCaregiver().getId() : null)
                .build();
    }

    private CaregiverProfileDTO mapToCaregiverDTO(CaregiverProfile caregiver) {
        User user = caregiver.getUser();

        return CaregiverProfileDTO.builder()
                .assignedMember(user.getEmail())
                .memberNameToAssist(caregiver.getMemberNameToAssist())
                .memberPhoneNumberToAssist(caregiver.getMemberPhoneNumberToAssist())
                .memberAddressToAssist(caregiver.getMemberAddressToAssist())
                .memberRelationship(caregiver.getMemberRelationship())
                .qualificationsAndSkills(caregiver.getQualificationsAndSkills())
                .build();
    }

    public List<OrderDTO> getPendingOrders() {
        try {
            MemberProfile member = getCurrentMember();
            return orderRepository.findByMember(member).stream()
                    .filter(o -> o.getStatus() != null && o.getStatus() != TaskStatus.COMPLETED)
                    .map(this::mapToOrderDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace(); // debug line
            throw e;
        }
    }

    public List<OrderDTO> getCaregiverPendingOrders() {
        try {
            CaregiverProfile caregiver = getCurrentCaregiver();
            return orderRepository.findByCaregiver(caregiver).stream()
                    .filter(o -> o.getStatus() != null && o.getStatus() != TaskStatus.COMPLETED)
                    .map(this::mapToOrderDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace(); // debug line
            throw e;
        }
    }

    public List<OrderDTO> getDeliveredOrders() {
        MemberProfile member = getCurrentMember();
        return orderRepository.findByMember(member).stream()
                .filter(o -> TaskStatus.COMPLETED.equals(o.getStatus()))
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getCaregiverDeliveredOrders() {
        CaregiverProfile caregiver = getCurrentCaregiver();
        return orderRepository.findByCaregiver(caregiver).stream()
                .filter(o -> TaskStatus.COMPLETED.equals(o.getStatus()))
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    private MemberProfile getCurrentMember() {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        UserValidationUtil.checkApproved(user);

        if (user.getRole() == Role.MEMBER) {
            MemberProfile member = user.getMemberProfile();
            if (member == null) {
                throw new RuntimeException("Member profile not found.");
            }
            return member;
        } else {
            throw new RuntimeException("Unsupported role for member data access.");
        }
    }

    private CaregiverProfile getCurrentCaregiver() {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        UserValidationUtil.checkApproved(user);

        if (user.getRole() == Role.CAREGIVER) {
            CaregiverProfile caregiver = user.getCaregiverProfile();
            if (caregiver == null || caregiver.getMemberNameToAssist() == null) {
                throw new RuntimeException("Caregiver did not register any member");
            }
            return caregiver;
        } else {
            throw new RuntimeException("No member is assisted");
        }
    }


    private OrderDTO mapToOrderDTO(Order order) {

        Integer rating = evaluationRepository.findByOrderId(order.getId())
                .map(ReassessmentEvaluation::getRating)
                .orElse(null);

        return OrderDTO.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .orderType(order.getOrderType())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .memberId(order.getMember() != null ? order.getMember().getId() : null)
                .memberUsername(order.getMember() != null ? order.getMember().getUser().getName() : null)
                .caregiverId(order.getCaregiver() != null ? order.getCaregiver().getId() : null) // new
                .caregiverName(order.getCaregiver() != null ? order.getCaregiver().getUser().getName() : null) // new
                .caregiverAssistedMember(order.getCaregiver() != null ? order.getCaregiver().getMemberNameToAssist() : null) // new
                .partnerId(order.getPartner() != null ? order.getPartner().getId() : null)
                .partnerName(order.getPartner() != null ? order.getPartner().getCompanyName() : null)
                .riderId(order.getRider() != null ? order.getRider().getId() : null)
                .riderName(order.getRider() != null ? order.getRider().getUser().getName() : null)

                // Extra fields for frontend:
                .memberName(order.getMember() != null ? order.getMember().getUser().getName() : null)
                .mealName(order.getOrderMeals().isEmpty() ? null : order.getOrderMeals().get(0).getMeal().getMealName())
                .mealType(
                        order.getOrderMeals().isEmpty() ||
                                order.getOrderMeals().get(0).getMeal() == null ||
                                order.getOrderMeals().get(0).getMeal().getMealType() == null
                                ? null
                                : order.getOrderMeals().get(0).getMeal().getMealType()
                )
                .memberAddress(
                        order.getMember() != null && order.getMember().getUser() != null
                                ? order.getMember().getUser().getAddress()
                                : null
                )
                .restaurant(order.getPartner() != null ? order.getPartner().getCompanyName() : null)
                .restaurantAddress(order.getPartner() != null ? order.getPartner().getCompanyAddress() : null)
                //.partnerAddress(order.getPartner() != null ? order.getPartner().getCompanyAddress() : null)
                .startDeliveryTime(order.getStartDeliveryTime())
                .rating(rating)
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
}
