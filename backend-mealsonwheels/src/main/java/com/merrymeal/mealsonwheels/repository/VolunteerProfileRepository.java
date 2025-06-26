package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.VolunteerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface VolunteerProfileRepository extends JpaRepository<VolunteerProfile, Long> {

    Optional<VolunteerProfile> findByUserId(Long userId);
    // Optional search by email or username (for login or admin use)
    Optional<VolunteerProfile> findByUser_Email(String email);
    Optional<VolunteerProfile> findByUser_Name(String name);

    // Optional filters for approval status if applicable
    List<VolunteerProfile> findByApprovedTrue();
    List<VolunteerProfile> findByApprovedFalse();
}
