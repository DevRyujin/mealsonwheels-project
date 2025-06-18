package com.merrymeal.mealsonwheels_backend.repository;

import com.merrymeal.mealsonwheels_backend.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
