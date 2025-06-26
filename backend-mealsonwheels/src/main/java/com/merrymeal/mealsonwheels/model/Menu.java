package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


import lombok.*;

@Entity
@Table(name = "menus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String menuName;
    private String menuType;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Meal> meals = new ArrayList<>();

}
