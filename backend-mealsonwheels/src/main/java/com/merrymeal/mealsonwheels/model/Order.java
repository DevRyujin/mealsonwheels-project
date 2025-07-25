package com.merrymeal.mealsonwheels.model;

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

    private String orderType;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberProfile member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private PartnerProfile partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id")
    private RiderProfile rider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id")
    private CaregiverProfile caregiver;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderMeal> orderMeals = new ArrayList<>();

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void addOrderMeal(OrderMeal om) {
        orderMeals.add(om);
        om.setOrder(this);
    }

    @Column(name = "start_delivery_time")
    private LocalDateTime startDeliveryTime;

    @Column(name = "end_delivery_time")
    private LocalDateTime endDeliveryTime;

    @Column(name = "code")
    private String code;

    @Column(name = "actual_meal_type")
    private String actualMealType; // Hot or Frozen, based on distance

    @Column(name = "distance_km")
    private Double distanceKm;

    // for caregiver
    private String recipientName;
    private String deliveryAddress;



}
