package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.AdminProfile;
import com.merrymeal.mealsonwheels.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminProfileRepository extends JpaRepository<AdminProfile, Long> {

    // ✅ Allows fetching admin profile by linked user
    Optional<AdminProfile> findByUser(User user);

    // ✅ Optional: filter by department if needed in dashboard or admin queries
    Optional<AdminProfile> findByDepartment(String department);

    // ✅ Optional: filter by job title if applicable
    Optional<AdminProfile> findByJobTitle(String jobTitle);
}
