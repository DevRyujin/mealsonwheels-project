package com.merrymeal.mealsonwheels.service.adminService;

import com.merrymeal.mealsonwheels.dto.ReassessmentEvaluationDTO;
import com.merrymeal.mealsonwheels.model.ReassessmentEvaluation;

import java.util.List;
import java.util.Optional;

public interface ReassessmentEvaluationService {

    ReassessmentEvaluation saveEvaluation(ReassessmentEvaluation evaluation);

    Optional<ReassessmentEvaluation> getEvaluationById(Long id);

    List<ReassessmentEvaluation> getEvaluationsByMemberId(Long memberId);

    List<ReassessmentEvaluation> getAllEvaluations();

    ReassessmentEvaluation updateEvaluation(Long id, ReassessmentEvaluation evaluationDetails);

    void deleteEvaluation(Long id);

    ReassessmentEvaluationDTO submitEvaluationForDeliveredOrder(Long orderId, ReassessmentEvaluationDTO dto);
}
