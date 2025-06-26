package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.SupporterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupporterProfileRepository extends JpaRepository<SupporterProfile, Long> {
    Optional<SupporterProfile> findByUser_Id(Long userId);
}
