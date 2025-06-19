package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String status; // e.g., "PENDING", "IN_PROGRESS", "COMPLETED"

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;
}
