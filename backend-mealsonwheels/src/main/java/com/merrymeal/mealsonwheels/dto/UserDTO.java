package com.merrymeal.mealsonwheels.dto;

import com.merrymeal.mealsonwheels.model.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Double latitude;
    private Double longitude;
    private Role role;
    private boolean approved; // optional if applicable (e.g., for partner/volunteer)
}
