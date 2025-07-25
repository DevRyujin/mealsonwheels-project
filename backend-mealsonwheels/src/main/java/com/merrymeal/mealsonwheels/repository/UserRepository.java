package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.Role;
import com.merrymeal.mealsonwheels.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {



    Optional<User> findByEmail(String email);       // ✅ used in login, registration

   // Optional<User> findByUsername(String username); // ✅ useful for profile retrieval or admin

    boolean existsByEmail(String email);            // ✅ check for existing user during registration

    List<User> findByRole(Role role);       // ✅ Needed for getUsersByRole()

    long countByRole(Role role);         // ✅ useful for admin dashboards/statistics
}
