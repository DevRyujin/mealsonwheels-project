package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import lombok.*;

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

    private String mealName;
    @Lob
    @Column(name = "meal_photo")
    private byte[] mealPhoto;
    private String mealDesc;

    private String mealType;
    @Builder.Default
    private String mealDietary = "";

    @Builder.Default
    private LocalDateTime mealCreatedDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Dish> dishes = new ArrayList<>();

    
}
