package com.merrymeal.mealsonwheels.dto;

import com.merrymeal.mealsonwheels.model.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Integer age;
    private String address;
    private Double latitude;
    private Double longitude;
    private Role role;
    private boolean approved;

    private List<String> dietaryRestrictions;
}
