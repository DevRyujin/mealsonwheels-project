package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.*;
import com.merrymeal.mealsonwheels.dto.roleDTOs.CaregiverProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.MemberProfileDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.PartnerProfileDTO;
import com.merrymeal.mealsonwheels.service.adminService.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ✅ USER MANAGEMENT
    @PutMapping("/approve-user/{id}")
    public ResponseEntity<String> approveUser(@PathVariable Long id) {
        adminService.approveUser(id);
        return ResponseEntity.ok("User approved successfully.");
    }

    @PutMapping("/reject-user/{id}")
    public ResponseEntity<String> rejectUser(@PathVariable Long id) {
        adminService.rejectUser(id);
        return ResponseEntity.ok("User rejected successfully.");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@RequestParam String role) {
        return ResponseEntity.ok(adminService.getUsersByRole(role));
    }

    // ✅ STATISTICS & TASKS
    @GetMapping("/statistics")
    public ResponseEntity<AdminStatisticDTO> getStatistics() {
        return ResponseEntity.ok(adminService.getAdminStatistics());
    }

    @PostMapping("/assign-task")
    public ResponseEntity<String> assignTask(@RequestParam Long userId, @RequestParam String task) {
        adminService.assignTaskToUser(userId, task);
        return ResponseEntity.ok("Task assigned successfully.");
    }

    // ✅ INGREDIENT MANAGEMENT
    @PostMapping("/ingredients")
    public ResponseEntity<IngredientDTO> addIngredient(@RequestBody IngredientDTO dto) {
        return ResponseEntity.ok(adminService.addIngredient(dto));
    }

    @PutMapping("/ingredients/{id}")
    public ResponseEntity<IngredientDTO> updateIngredient(@PathVariable Long id, @RequestBody IngredientDTO dto) {
        return ResponseEntity.ok(adminService.updateIngredient(id, dto));
    }

    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable Long id) {
        adminService.deleteIngredient(id);
        return ResponseEntity.ok("Ingredient deleted successfully.");
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<IngredientDTO> getIngredient(@PathVariable Long id) {
        Optional<IngredientDTO> ingredient = adminService.getIngredientById(id);
        return ingredient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        return ResponseEntity.ok(adminService.getAllIngredients());
    }

    @GetMapping("/ingredients/expiring-before")
    public ResponseEntity<List<IngredientDTO>> getIngredientsExpiringBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(adminService.getIngredientsExpiringBefore(date));
    }

    // ✅ FOOD SAFETY RECORDS
    @PostMapping("/food-safety")
    public ResponseEntity<FoodSafetyRecordDTO> addFoodSafetyRecord(@RequestBody FoodSafetyRecordDTO dto) {
        return ResponseEntity.ok(adminService.addFoodSafetyRecord(dto));
    }

    @PutMapping("/food-safety/{id}")
    public ResponseEntity<FoodSafetyRecordDTO> updateFoodSafetyRecord(@PathVariable Long id, @RequestBody FoodSafetyRecordDTO dto) {
        return ResponseEntity.ok(adminService.updateFoodSafetyRecord(id, dto));
    }

    @DeleteMapping("/food-safety/{id}")
    public ResponseEntity<String> deleteFoodSafetyRecord(@PathVariable Long id) {
        adminService.deleteFoodSafetyRecord(id);
        return ResponseEntity.ok("Food safety record deleted successfully.");
    }

    @GetMapping("/food-safety/{id}")
    public ResponseEntity<FoodSafetyRecordDTO> getFoodSafetyRecord(@PathVariable Long id) {
        Optional<FoodSafetyRecordDTO> record = adminService.getFoodSafetyRecordById(id);
        return record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/food-safety")
    public ResponseEntity<List<FoodSafetyRecordDTO>> getAllFoodSafetyRecords() {
        return ResponseEntity.ok(adminService.getAllFoodSafetyRecords());
    }

    @GetMapping("/food-safety/by-ingredient/{ingredientId}")
    public ResponseEntity<List<FoodSafetyRecordDTO>> getRecordsByIngredientId(@PathVariable Long ingredientId) {
        return ResponseEntity.ok(adminService.getRecordsByIngredientId(ingredientId));
    }

    // ✅ REASSESSMENT EVALUATIONS
    @GetMapping("/reassessment-evaluations")
    public ResponseEntity<List<ReassessmentEvaluationDTO>> getAllEvaluations() {
        return ResponseEntity.ok(adminService.getAllEvaluations());
    }

    @GetMapping("/reassessment-evaluations/{evaluationId}")
    public ResponseEntity<ReassessmentEvaluationDTO> getEvaluationById(@PathVariable Long evaluationId) {
        Optional<ReassessmentEvaluationDTO> evaluation = adminService.getEvaluationById(evaluationId);
        return evaluation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/reassessment-evaluations/member/{memberId}")
    public ResponseEntity<List<ReassessmentEvaluationDTO>> getEvaluationsByMemberId(@PathVariable Long memberId) {
        return ResponseEntity.ok(adminService.getEvaluationsByMemberId(memberId));
    }

    @GetMapping("/members-without-caregivers")
    public ResponseEntity<List<UserDTO>> getMembersWithoutCaregivers() {
        return ResponseEntity.ok(adminService.getApprovedMembersWithoutCaregivers());
    }

    @GetMapping("/caregivers-with-member-info")
    public ResponseEntity<List<CaregiverProfileDTO>> getCaregiversWithMemberInfo() {
        return ResponseEntity.ok(adminService.getAllApprovedCaregiversWithMemberInfo());
    }

    @GetMapping("/approved-partners")
    public ResponseEntity<List<PartnerProfileDTO>> getApprovedPartners() {
        List<PartnerProfileDTO> partners = adminService.getApprovedPartners();
        return ResponseEntity.ok(partners);
    }




}
