package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.ReassessmentEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReassessmentEvaluationRepository extends JpaRepository<ReassessmentEvaluation, Long> {

    List<ReassessmentEvaluation> findByMemberId(Long memberId);

    Optional<ReassessmentEvaluation> findByOrderId(Long orderId);
}

// This repository interface extends JpaRepository to provide CRUD operations for ReassessmentEvaluation entities.