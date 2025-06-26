package com.merrymeal.mealsonwheels.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReassessmentEvaluationDTO {

    private Long id;

    @NotNull(message = "Evaluation date is required")
    private LocalDateTime evaluationDate;

    @Size(max = 2000, message = "Evaluation notes must be under 2000 characters")
    private String evaluationNotes;

    @Size(max = 1000, message = "Service adjustments must be under 1000 characters")
    private String serviceAdjustments;

    @NotNull(message = "Member ID is required")
    private Long memberId;

    // Optional: not all evaluations are tied to an order
    private Long orderId;

    private Integer rating;

    private String comment;
}
