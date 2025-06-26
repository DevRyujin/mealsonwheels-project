package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Member extends User {

    @Column(nullable = true)
    private String dietaryRestriction;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double memberLocationLong;

    @Column(nullable = false)
    private double memberLocationLat;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id")
    private Caregiver caregiver;

    public Member(String username, String phoneNumber, String email, String password,
                  boolean approved, Role role, String dietaryRestriction,
                  String address, double memberLocationLat, double memberLocationLong) {
        super(username, phoneNumber, email, password, approved, role);
        this.dietaryRestriction = dietaryRestriction;
        this.address = address;
        this.memberLocationLat = memberLocationLat;
        this.memberLocationLong = memberLocationLong;
    }
}
