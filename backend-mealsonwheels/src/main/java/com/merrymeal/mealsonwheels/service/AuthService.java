package com.merrymeal.mealsonwheels.service;

import com.merrymeal.mealsonwheels.dto.LoginRequest;
import com.merrymeal.mealsonwheels.dto.RegisterRequest;
import com.merrymeal.mealsonwheels.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    void register(RegisterRequest registerRequest);
}
