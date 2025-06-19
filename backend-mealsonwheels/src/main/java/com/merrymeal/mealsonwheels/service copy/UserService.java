package com.merrymeal.mealsonwheels_backend.service;

import com.merrymeal.mealsonwheels_backend.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Long id);
    UserDTO approveUser(Long userId, boolean approved);
    UserDTO updateUser(Long id, UserDTO userDTO);
}
