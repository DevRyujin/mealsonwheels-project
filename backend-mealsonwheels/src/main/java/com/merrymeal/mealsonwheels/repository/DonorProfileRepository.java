package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.DonorProfile;
import com.merrymeal.mealsonwheels.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DonorProfileRepository extends JpaRepository<DonorProfile, Long> {

    // Fetch donor by linked user ID (commonly used)
    Optional<DonorProfile> findByUserId(Long userId);

    // Optional: for user-based queries (if you're passing full User object)
    Optional<DonorProfile> findByUser_Name(String username);

    // Optional: search by donor's organization, if applicable
    //Optional<DonorProfile> findByOrganizationName(String organizationName);

    Optional<DonorProfile> findByUser_Email(String email);

}
