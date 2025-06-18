package com.merrymeal.mealsonwheels_backend.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDTO {
    private Long id;
    private String username;
    private String phoneNumber;
    private String email;
    private String roleName;
    private boolean approved;
}
