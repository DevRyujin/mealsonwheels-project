package com.merrymeal.mealsonwheels_backend.dto;

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

    @Size(max = 2000)
    private String evaluationNotes;

    @Size(max = 1000)
    private String serviceAdjustments;

    @NotNull(message = "Member ID is required")
    private Long memberId;

    // 🔹 New: Optional for order-linked evaluations
    private Long orderId;

    private Integer rating;

    private String comment;
}
