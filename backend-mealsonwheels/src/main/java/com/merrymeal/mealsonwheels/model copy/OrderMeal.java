    package com.merrymeal.mealsonwheels_backend.model;

    import jakarta.persistence.*;
    import lombok.*;
@Entity
@Table(name = "order_meals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    private Integer quantity;
}
