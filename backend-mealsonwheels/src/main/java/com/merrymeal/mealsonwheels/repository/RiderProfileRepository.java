package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.RiderProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RiderProfileRepository extends JpaRepository<RiderProfile, Long> {

  // ✅ Find by linked user ID
  Optional<RiderProfile> findByUserId(Long userId);

  // Recently Added after fixing orderservice
  Optional<RiderProfile> findByUser_Email(String email);


  // ✅ Optional: find by username (linked via user)
  //Optional<RiderProfile> findByUser_Username(String username);

  // ✅ Optional: find by approval status if applicable
  //Optional<RiderProfile> findByUser_Approved(boolean approved);

  List<RiderProfile> findByUser_ApprovedTrue();
}
