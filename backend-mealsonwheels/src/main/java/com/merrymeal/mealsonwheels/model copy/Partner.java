package com.merrymeal.mealsonwheels_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "partner")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Partner extends User {

    private String companyName;
    private String companyDescription;
    private String companyAddress;

    private double companyLocationLat;
    private double companyLocationLong;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<Dish> dishes = new ArrayList<>();

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<Meal> providedMeals = new ArrayList<>();

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<Rider> riders = new ArrayList<>();

    public Partner(String username, String phoneNumber, String email, String password,
            boolean approved, Role role, String companyName, String companyDescription,
            String companyAddress, double companyLocationLat, double companyLocationLong) {
        super(username, phoneNumber, email, password, approved, role);
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.companyAddress = companyAddress;
        this.companyLocationLat = companyLocationLat;
        this.companyLocationLong = companyLocationLong;
    }
}
