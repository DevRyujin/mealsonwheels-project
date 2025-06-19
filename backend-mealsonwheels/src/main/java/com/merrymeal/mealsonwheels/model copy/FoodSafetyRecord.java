package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_safety_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodSafetyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inspectionDate;

    private boolean safetyChecklistCompleted;

    @Column(length = 1000)
    private String inspectionNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;
}
