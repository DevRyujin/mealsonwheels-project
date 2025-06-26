package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.CaregiverProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CaregiverProfileRepository extends JpaRepository<CaregiverProfile, Long> {

    Optional<CaregiverProfile> findByUser_Email(String email);           // ✅ check if exists

    //Optional<CaregiverProfile> findByUser_Username(String username);       // ✅ username-based lookup

    List<CaregiverProfile> findByApprovedFalse();                     // ✅ pending caregivers

    List<CaregiverProfile> findByApprovedTrue();                      // ✅ approved caregivers
}
