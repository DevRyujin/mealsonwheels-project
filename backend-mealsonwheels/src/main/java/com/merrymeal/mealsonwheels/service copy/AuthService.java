package com.merrymeal.mealsonwheels_backend.service;

import com.merrymeal.mealsonwheels_backend.dto.LoginDTO;
import com.merrymeal.mealsonwheels_backend.dto.RegisterDTO;
import com.merrymeal.mealsonwheels_backend.dto.UserDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    UserDTO register(RegisterDTO registerDTO);
}
