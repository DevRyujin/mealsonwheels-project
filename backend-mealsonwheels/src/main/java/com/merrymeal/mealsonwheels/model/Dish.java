package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


import lombok.*;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dishName;

    @Lob
    @Column(name = "dish_photo")
    private byte[] dishPhoto;

    private String dishDesc;
    private String dishType;
    private String dishDietary;

    @Builder.Default
    private LocalDateTime dishCreatedDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
