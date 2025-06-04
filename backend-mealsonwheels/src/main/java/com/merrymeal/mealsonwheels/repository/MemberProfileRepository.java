package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
}
