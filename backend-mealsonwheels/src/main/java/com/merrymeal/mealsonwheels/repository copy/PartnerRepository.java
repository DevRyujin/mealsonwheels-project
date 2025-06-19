package com.merrymeal.mealsonwheels_backend.repository;

import com.merrymeal.mealsonwheels_backend.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    
    // Optional: find Partner by companyName (example)
    Optional<Partner> findByCompanyName(String companyName);
}
