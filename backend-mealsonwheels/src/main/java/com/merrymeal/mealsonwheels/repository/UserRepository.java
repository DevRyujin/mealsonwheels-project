package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);       // for login or validation
    boolean existsByEmail(String email);             // check if user exist
}
