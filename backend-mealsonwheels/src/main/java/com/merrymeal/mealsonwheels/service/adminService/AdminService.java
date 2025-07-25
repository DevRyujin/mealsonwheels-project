package com.merrymeal.mealsonwheels.service.adminService;

import com.merrymeal.mealsonwheels.dto.*;
import com.merrymeal.mealsonwheels.dto.roleDTOs.*;
import com.merrymeal.mealsonwheels.model.*;
import com.merrymeal.mealsonwheels.repository.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private TaskService taskService;

        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private RiderProfileRepository riderProfileRepository;

        @Autowired
        private DonorProfileRepository donorRepository;

        @Autowired
        private IngredientService ingredientService;

        @Autowired
        private FoodSafetyRecordService foodSafetyRecordService;

        @Autowired
        private ReassessmentEvaluationService reassessmentEvaluationService;

        @Autowired
        private PartnerProfileRepository partnerProfileRepository;

        @Autowired
        private VolunteerProfileRepository volunteerProfileRepository;

        @Autowired
        private TaskRepository taskRepository;

        @Autowired
        private MemberProfileRepository memberProfileRepository;

        // Approve user registration
        public void approveUser(Long userId) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                user.setApproved(true);
                userRepository.save(user);
        }

        // Reject user registration
        public void rejectUser(Long userId) {
                if (!userRepository.existsById(userId)) {
                        throw new RuntimeException("User not found");
                }
                userRepository.deleteById(userId);
        }

        // Get users by role
        public List<UserDTO> getUsersByRole(String roleName) {
                Role roleEnum;
                try {
                        roleEnum = Role.valueOf(roleName.toUpperCase());
                } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Invalid role: " + roleName);
                }

                return userRepository.findByRole(roleEnum).stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
        }

        private UserDTO convertToDTO(User user) {
                String address = null;
                List<String> dietaryRestrictions = null;
                Double lat = null;
                Double lng = null;

                if (user.getRole() == Role.MEMBER && user.getMemberProfile() != null) {
                        MemberProfile profile = user.getMemberProfile();
                        address = profile.getAddress();
                        dietaryRestrictions = profile.getDietaryRestrictions();
                        lat = profile.getMemberLocationLat();
                        lng = profile.getMemberLocationLong();
                }

                return UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .address(address)
                        .latitude(lat)
                        .longitude(lng)
                        .role(user.getRole() != null ? user.getRole() : Role.UNASSIGNED)
                        .approved(user.isApproved())
                        .dietaryRestrictions(dietaryRestrictions)
                        .build();
        }

        // Admin statistics
        public AdminStatisticDTO getAdminStatistics() {
                long totalMembers = userRepository.countByRole(Role.MEMBER);
                long totalPartners = userRepository.countByRole(Role.PARTNER);
                long totalVolunteers = userRepository.countByRole(Role.VOLUNTEER);
                long totalRiders = userRepository.countByRole(Role.RIDER);
                long totalDonors = userRepository.countByRole(Role.DONOR);

                List<Order> completedOrders = orderRepository.findByStatus(TaskStatus.COMPLETED);
                long ordersDelivered = completedOrders.size();

                long mealsServed = completedOrders.stream()
                        .flatMap(order -> order.getOrderMeals().stream())
                        .mapToLong(OrderMeal::getQuantity)
                        .sum();

                BigDecimal totalDonationsReceived = donorRepository.findAll().stream()
                        .map(DonorProfile::getTotalDonatedAmount)
                        .filter(amount -> amount != null)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                return AdminStatisticDTO.builder()
                        .totalMembers(totalMembers)
                        .totalPartners(totalPartners)
                        .totalVolunteers(totalVolunteers)
                        .totalRiders(totalRiders)
                        .totalDonors(totalDonors)
                        .ordersDelivered(ordersDelivered)
                        .mealsServed(mealsServed)
                        .totalDonationsReceived(totalDonationsReceived)
                        .build();
        }

        // Assign task to user
        public void assignTaskToUser(Long userId, String taskDescription) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                String role = user.getRole().name(); // "RIDER", "VOLUNTEER", etc.

                Task.TaskBuilder taskBuilder = Task.builder()
                        .description(taskDescription)
                        .status(TaskStatus.PENDING)
                        .assignedDate(LocalDateTime.now());

                switch (role) {
                        case "RIDER" -> taskBuilder.rider(user);
                        case "VOLUNTEER" -> taskBuilder.volunteer(user);
                        default -> throw new IllegalArgumentException("Only RIDER or VOLUNTEER can be assigned tasks");
                }

                taskRepository.save(taskBuilder.build());
        }


        // Get approved members
        public List<UserDTO> getAllApprovedMembers() {
                return userRepository.findByRole(Role.MEMBER).stream()
                        .filter(User::isApproved)
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
        }

        // ============== INGREDIENTS ==============

        public IngredientDTO addIngredient(IngredientDTO dto) {
                return ingredientService.saveIngredient(dto);
        }

        public IngredientDTO updateIngredient(Long id, IngredientDTO dto) {
                return ingredientService.updateIngredient(id, dto);
        }

        public void deleteIngredient(Long id) {
                ingredientService.deleteIngredient(id);
        }

        public Optional<IngredientDTO> getIngredientById(Long id) {
                return ingredientService.getIngredientById(id);
        }

        public List<IngredientDTO> getAllIngredients() {
                return ingredientService.getAllIngredients();
        }

        public List<IngredientDTO> getIngredientsExpiringBefore(LocalDate date) {
                return ingredientService.getIngredientsExpiringBefore(date);
        }

        // ============== FOOD SAFETY RECORD ==============

        public FoodSafetyRecordDTO addFoodSafetyRecord(FoodSafetyRecordDTO dto) {
                return foodSafetyRecordService.saveRecord(dto);
        }

        public FoodSafetyRecordDTO updateFoodSafetyRecord(Long id, FoodSafetyRecordDTO dto) {
                return foodSafetyRecordService.updateRecord(id, dto);
        }

        public void deleteFoodSafetyRecord(Long id) {
                foodSafetyRecordService.deleteRecord(id);
        }

        public Optional<FoodSafetyRecordDTO> getFoodSafetyRecordById(Long id) {
                return foodSafetyRecordService.getRecordById(id);
        }

        public List<FoodSafetyRecordDTO> getAllFoodSafetyRecords() {
                return foodSafetyRecordService.getAllRecords();
        }

        public List<FoodSafetyRecordDTO> getRecordsByIngredientId(Long ingredientId) {
                return foodSafetyRecordService.getRecordsByIngredientId(ingredientId);
        }

        // ============== REASSESSMENT ==============

        public List<ReassessmentEvaluationDTO> getAllEvaluations() {
                return reassessmentEvaluationService.getAllEvaluations().stream()
                        .map(this::mapToEvaluationDTO)
                        .collect(Collectors.toList());
        }

        public List<ReassessmentEvaluationDTO> getEvaluationsByMemberId(Long memberId) {
                return reassessmentEvaluationService.getEvaluationsByMemberId(memberId).stream()
                        .map(this::mapToEvaluationDTO)
                        .collect(Collectors.toList());
        }

        public Optional<ReassessmentEvaluationDTO> getEvaluationById(Long evaluationId) {
                return reassessmentEvaluationService.getEvaluationById(evaluationId)
                        .map(this::mapToEvaluationDTO);
        }

        // ============== INFO CARD ==============

        private ReassessmentEvaluationDTO mapToEvaluationDTO(ReassessmentEvaluation eval) {
                return ReassessmentEvaluationDTO.builder()
                        .id(eval.getId())
                        .evaluationDate(eval.getEvaluationDate())
                        .evaluationNotes(eval.getEvaluationNotes())
                        .serviceAdjustments(eval.getServiceAdjustments())
                        .memberId(eval.getMember() != null ? eval.getMember().getId() : null)
                        .build();
        }

        public List<MemberProfileDTO> getAllApprovedMembersWithCaregiverInfo() {
                return userRepository.findByRole(Role.MEMBER).stream()
                        .filter(User::isApproved)
                        .filter(user -> {
                                MemberProfile member = user.getMemberProfile();
                                return member != null && member.getCaregiver() != null;
                        })
                        .map(user -> {
                                MemberProfile member = user.getMemberProfile();
                                CaregiverProfile caregiver = member.getCaregiver();
                                User caregiverUser = caregiver.getUser();

                                return MemberProfileDTO.builder()
                                        .name(user.getName())
                                        .email(user.getEmail())
                                        .phone(user.getPhone())
                                        .approved(user.isApproved())
                                        .address(member.getAddress())
                                        .dietaryRestrictions(member.getDietaryRestrictions())
                                        .memberLocationLat(member.getMemberLocationLat())
                                        .memberLocationLong(member.getMemberLocationLong())
                                        .caregiverId(caregiver.getId())
                                        .caregiverName(caregiverUser.getName())
                                        .caregiverEmail(caregiverUser.getEmail())
                                        .caregiverPhone(caregiverUser.getPhone())
                                        .build();
                        })
                        .collect(Collectors.toList());
        }


        public List<CaregiverProfileDTO> getAllApprovedCaregiversWithMemberInfo() {
                return userRepository.findByRole(Role.CAREGIVER).stream()
                        .filter(User::isApproved)
                        .map(User::getCaregiverProfile)
                        .filter(cg -> cg != null &&
                                cg.getMemberNameToAssist() != null &&
                                !cg.getMemberNameToAssist().isBlank())
                        .map(cg -> {
                                User caregiverUser = cg.getUser();
                                return CaregiverProfileDTO.builder()
                                        .caregiverName(caregiverUser.getName())
                                        .caregiverEmail(caregiverUser.getEmail())
                                        .caregiverPhone(caregiverUser.getPhone())
                                        .memberNameToAssist(cg.getMemberNameToAssist())
                                        .memberPhoneNumberToAssist(cg.getMemberPhoneNumberToAssist())
                                        .memberAddressToAssist(cg.getMemberAddressToAssist())
                                        .memberRelationship(cg.getMemberRelationship())
                                        .qualificationsAndSkills(cg.getQualificationsAndSkills())
                                        .approved(caregiverUser.isApproved())
                                        .build();
                        })
                        .collect(Collectors.toList());
        }


        // Get approved members without caregivers
        public List<UserDTO> getApprovedMembersWithoutCaregivers() {
                return userRepository.findByRole(Role.MEMBER).stream()
                        .filter(User::isApproved)
                        .filter(user -> {
                                MemberProfile member = user.getMemberProfile();
                                return member != null && member.getCaregiver() == null;
                        })
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
        }

        public PartnerProfileDTO mapToPartnerDTO(PartnerProfile profile) {
                if (profile == null) return null;

                return PartnerProfileDTO.builder()
                        .companyName(profile.getCompanyName())
                        .partnershipDuration(profile.getPartnershipDuration())
                        .companyDescription(profile.getCompanyDescription())
                        .companyAddress(profile.getCompanyAddress())
                        .companyLocationLat(profile.getCompanyLocationLat())
                        .companyLocationLong(profile.getCompanyLocationLong())

                        // Add these from the associated User
                        .email(profile.getUser() != null ? profile.getUser().getEmail() : null)
                        .address(profile.getUser() != null ? profile.getUser().getAddress() : null)

                        // Optional: Meal/Menu data
                        .dishes(new ArrayList<>())
                        .providedMeals(new ArrayList<>())
                        .menus(new ArrayList<>())
                        .build();
        }


        public List<PartnerProfileDTO> getApprovedPartners() {
                return partnerProfileRepository.findAll().stream()
                        .filter(p -> p.getUser() != null && p.getUser().isApproved())
                        .map(this::mapToPartnerDTO)
                        .collect(Collectors.toList());
        }

        public VolunteerProfileDTO mapToVolunteerDTO(VolunteerProfile volunteer) {
                if (volunteer == null) return null;

                return VolunteerProfileDTO.builder()
                        .serviceType(volunteer.getServiceType())
                        .availableDays(
                                volunteer.getAvailableDays() != null
                                        ? volunteer.getAvailableDays().stream()
                                        .map(DayOfWeek::getLabel) // "Monday", "Tuesday", etc.
                                        .collect(Collectors.toSet())
                                        : null
                        )
                        .volunteerDuration(volunteer.getVolunteerDuration())
                        .name(volunteer.getUser() != null ? volunteer.getUser().getName() : null)
                        .email(volunteer.getUser() != null ? volunteer.getUser().getEmail() : null)
                        .address(volunteer.getUser() != null ? volunteer.getUser().getAddress() : null)
                        .phone(volunteer.getUser() != null ? volunteer.getUser().getPhone() : null)
                        .build();
        }

        public List<VolunteerProfileDTO> getApprovedVolunteers() {
                return volunteerProfileRepository.findAll().stream()
                        .filter(v -> v.getUser() != null && v.getUser().isApproved())
                        .map(this::mapToVolunteerDTO)
                        .collect(Collectors.toList());
        }

        public UserDTO getLoggedInAdminInfo() {
                String email = getCurrentLoggedInEmail();
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Admin not found"));

                return mapUserToDTO(user);
        }

        public UserDTO updateAdminInfo(UserDTO updateDTO) {
                String email = getCurrentLoggedInEmail();
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Admin not found"));

                updateDTO.setEmail(null);

                user.setName(updateDTO.getName());
                user.setPhone(updateDTO.getPhone());
                user.setAge(updateDTO.getAge());
                user.setAddress(updateDTO.getAddress());
                user.setLatitude(updateDTO.getLatitude());
                user.setLongitude(updateDTO.getLongitude());

                userRepository.save(user);
                return mapUserToDTO(user);
        }


        // Helper to get current email
        private String getCurrentLoggedInEmail() {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal instanceof UserDetails userDetails) {
                        return userDetails.getUsername(); // email
                } else {
                        return principal.toString();
                }
        }

        private UserDTO mapUserToDTO(User user) {
                return UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .age(user.getAge())
                        .address(user.getAddress())
                        .latitude(user.getLatitude())
                        .longitude(user.getLongitude())
                        .role(user.getRole())
                        .approved(user.isApproved())
                        .build();
        }

        public void assignRiderToOrder(Long orderId, Long riderId) {
                Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

                RiderProfile rider = riderProfileRepository.findById(riderId)
                        .orElseThrow(() -> new RuntimeException("Rider not found with ID: " + riderId));

                order.setRider(rider);
                order.setStatus(TaskStatus.ASSIGNED); // ✅ This is the key line
                order.setStartDeliveryTime(LocalDateTime.now()); // Optional
                orderRepository.save(order);
        }


        public void assignTaskToRider(Long memberUserId, Long riderId) {
                MemberProfile member = memberProfileRepository.findByUserId(memberUserId)
                        .orElseThrow(() -> new RuntimeException("Member not found"));

                Order order = orderRepository.findTopByMemberOrderByCreatedAtDesc(member)
                        .orElseThrow(() -> new RuntimeException("No order found for this member"));

                RiderProfile rider = riderProfileRepository.findById(riderId)
                        .orElseThrow(() -> new RuntimeException("Rider not found"));

                order.setRider(rider);
                order.setStatus(TaskStatus.ASSIGNED);
                order.setStartDeliveryTime(LocalDateTime.now());

                orderRepository.save(order);
        }






}
