package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.MemberProfile;  // or your Member entity if separate
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberProfile, Long> {

    // Old team useful queries:

    MemberProfile findByEmail(String email);

    MemberProfile findByUsername(String username);

    List<MemberProfile> findByApprovedFalse();

    List<MemberProfile> findByApprovedTrue();

    List<MemberProfile> findByCaregiverId(Long caregiverId);
}
