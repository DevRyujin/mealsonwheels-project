package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.PartnerProfile;
import com.merrymeal.mealsonwheels.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerProfileRepository extends JpaRepository<PartnerProfile, Long> {

    // ✅ Optional search by company name
    Optional<PartnerProfile> findByUser_Id(Long userId);       // ✅ Used for logged-in partner access
    Optional<PartnerProfile> findByCompanyName(String name);   // ✅ Used for search, validation, approval
    Optional<PartnerProfile> findByUserId(Long userId);

    Optional<PartnerProfile> findByUser(User user);



    // ✅ Add more if needed (e.g., approval status or email) in the future
}
