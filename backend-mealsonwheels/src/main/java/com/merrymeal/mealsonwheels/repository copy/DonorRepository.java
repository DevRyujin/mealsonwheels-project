package com.merrymeal.mealsonwheels_backend.repository;

import com.merrymeal.mealsonwheels_backend.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
}
