package com.merrymeal.mealsonwheels_backend.service;

import com.merrymeal.mealsonwheels_backend.model.User;
import com.merrymeal.mealsonwheels_backend.repository.UserRepository;
import com.merrymeal.mealsonwheels_backend.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Try to find by username first
        User user = userRepository.findByUsername(identifier)
            .orElseGet(() -> userRepository.findByEmail(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + identifier)));

        return new CustomUserDetails(user);
    }
}
