package com.merrymeal.mealsonwheels.service.adminService;

import com.merrymeal.mealsonwheels.dto.ReassessmentEvaluationDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.*;
import com.merrymeal.mealsonwheels.repository.*;
import com.merrymeal.mealsonwheels.security.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReassessmentEvaluationServiceImpl implements ReassessmentEvaluationService {

    private final ReassessmentEvaluationRepository evaluationRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public ReassessmentEvaluationServiceImpl(ReassessmentEvaluationRepository evaluationRepository,
                                             OrderRepository orderRepository,
                                             UserRepository userRepository) {
        this.evaluationRepository = evaluationRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReassessmentEvaluation saveEvaluation(ReassessmentEvaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Optional<ReassessmentEvaluation> getEvaluationById(Long id) {
        return evaluationRepository.findById(id);
    }

    @Override
    public List<ReassessmentEvaluation> getEvaluationsByMemberId(Long memberId) {
        return evaluationRepository.findByMemberId(memberId);
    }

    @Override
    public List<ReassessmentEvaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    @Override
    public ReassessmentEvaluation updateEvaluation(Long id, ReassessmentEvaluation evaluationDetails) {
        ReassessmentEvaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reassessment Evaluation not found with id " + id));

        evaluation.setEvaluationDate(evaluationDetails.getEvaluationDate());
        evaluation.setEvaluationNotes(evaluationDetails.getEvaluationNotes());
        evaluation.setServiceAdjustments(evaluationDetails.getServiceAdjustments());
        evaluation.setMember(evaluationDetails.getMember());
        evaluation.setOrder(evaluationDetails.getOrder());
        evaluation.setRating(evaluationDetails.getRating());
        evaluation.setComment(evaluationDetails.getComment());

        return evaluationRepository.save(evaluation);
    }

    @Override
    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }

    @Override
    public ReassessmentEvaluationDTO submitEvaluationForDeliveredOrder(Long orderId, ReassessmentEvaluationDTO dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!"DELIVERED".equalsIgnoreCase(order.getStatus())) {
            throw new IllegalStateException("Order must be delivered to submit evaluation.");
        }

        if (evaluationRepository.findByOrderId(orderId).isPresent()) {
            throw new IllegalStateException("Evaluation for this order already exists.");
        }

        User memberUser = userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        ReassessmentEvaluation evaluation = ReassessmentEvaluation.builder()
                .evaluationDate(LocalDateTime.now())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .order(order)
                .member(memberUser) // ✅ this is correct
                .build();

        evaluationRepository.save(evaluation);

        return ReassessmentEvaluationDTO.builder()
                .id(evaluation.getId())
                .evaluationDate(evaluation.getEvaluationDate())
                .comment(evaluation.getComment())
                .rating(evaluation.getRating())
                .orderId(order.getId())
                .memberId(memberUser.getId()) // ✅ this is also correct
                .build();

    }
}
