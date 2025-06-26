package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalAmount;

    private String orderType;  // add this field

    private String status;     // add this field

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderMeal> orderMeals = new ArrayList<>();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    // Helper methods to keep both sides consistent
    public void addOrderMeal(OrderMeal orderMeal) {
        orderMeals.add(orderMeal);
        orderMeal.setOrder(this);
    }

    public void removeOrderMeal(OrderMeal orderMeal) {
        orderMeals.remove(orderMeal);
        orderMeal.setOrder(null);
    }
}
