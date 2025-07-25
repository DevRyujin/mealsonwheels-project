package com.merrymeal.mealsonwheels.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mealName;

    @Lob
    @Column(name = "meal_photo", columnDefinition = "LONGBLOB")
    private byte[] mealPhoto;

    @Column(name = "meal_photo_type")
    private String mealPhotoType;

    @Column(length = 1000)
    private String mealDesc;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @Builder.Default
    private String mealDietary = "";

    @Builder.Default
    private LocalDateTime mealCreatedDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    private User partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Dish> dishes = new ArrayList<>();
}
