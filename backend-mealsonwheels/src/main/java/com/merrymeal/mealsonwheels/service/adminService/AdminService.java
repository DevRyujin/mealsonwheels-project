package com.merrymeal.mealsonwheels.service.admin;

import com.merrymeal.mealsonwheels.dto.*;
import com.merrymeal.mealsonwheels.model.*;
import com.merrymeal.mealsonwheels.repository.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        private DonorRepository donorRepository;

        @Autowired
        private IngredientService ingredientService;

        @Autowired
        private FoodSafetyRecordService foodSafetyRecordService;

        @Autowired
        private ReassessmentEvaluationService reassessmentEvaluationService;

        // ✅ Approve user registration
        public void approveUser(Long userId) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                user.setApproved(true);
                userRepository.save(user);
        }

        // ✅ Reject user registration
        public void rejectUser(Long userId) {
                if (!userRepository.existsById(userId)) {
                        throw new RuntimeException("User not found");
                }
                userRepository.deleteById(userId);
        }

        // ✅ Get users by role
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
                return UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .approved(user.isApproved())
                        .build();
        }

        // ✅ Admin statistics
        public AdminStatisticDTO getAdminStatistics() {
                long totalMembers = userRepository.countByRole(Role.MEMBER);
                long totalPartners = userRepository.countByRole(Role.PARTNER);
                long totalVolunteers = userRepository.countByRole(Role.VOLUNTEER);
                long totalRiders = userRepository.countByRole(Role.RIDER);
                long totalDonors = userRepository.countByRole(Role.DONOR);

                List<Order> completedOrders = orderRepository.findByStatus("Completed");
                long ordersDelivered = completedOrders.size();

                long mealsServed = completedOrders.stream()
                        .flatMap(order -> order.getOrderMeals().stream())
                        .mapToLong(OrderMeal::getQuantity)
                        .sum();

                BigDecimal totalDonationsReceived = donorRepository.findAll().stream()
                        .map(Donor::getTotalDonatedAmount)
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

        // ✅ Assign task to user
        public void assignTaskToUser(Long userId, String taskDescription) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                TaskDTO taskDTO = TaskDTO.builder()
                        .description(taskDescription)
                        .status("PENDING")
                        .volunteerId(user.getId())
                        .volunteerName(user.getName())
                        .build();

                taskService.createTask(taskDTO);
        }

        // ✅ Get approved members
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

        private ReassessmentEvaluationDTO mapToEvaluationDTO(ReassessmentEvaluation eval) {
                return ReassessmentEvaluationDTO.builder()
                        .id(eval.getId())
                        .evaluationDate(eval.getEvaluationDate())
                        .evaluationNotes(eval.getEvaluationNotes())
                        .serviceAdjustments(eval.getServiceAdjustments())
                        .memberId(eval.getMember() != null ? eval.getMember().getId() : null)
                        .build();
        }
}
