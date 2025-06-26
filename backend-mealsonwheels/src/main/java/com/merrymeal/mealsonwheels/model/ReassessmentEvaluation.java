package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reassessment_evaluations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReassessmentEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime evaluationDate;

    @Column(length = 2000)
    private String evaluationNotes;

    @Column(length = 1000)
    private String serviceAdjustments;

    // 🔹 New: Link to member who submitted it
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 🔹 New: Optional association to an order (can be null for admin-side
    // evaluations)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", unique = true)
    private Order order;

    // 🔹 New: Simple rating and comment for member feedback
    private Integer rating;

    @Column(length = 1000)
    private String comment;
}
