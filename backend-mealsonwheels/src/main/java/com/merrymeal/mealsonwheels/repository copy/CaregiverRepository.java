package com.merrymeal.mealsonwheels_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.merrymeal.mealsonwheels_backend.model.Caregiver;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
    Caregiver findByEmail(String email);
    Caregiver findByUsername(String username);
    List<Caregiver> findByApprovedFalse();
    List<Caregiver> findByApprovedTrue();
}
