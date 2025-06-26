package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

    Optional<MemberProfile> findByUser_Email(String email);                // ✅ useful for login/validation

    Optional<MemberProfile> findByUser_Name(String name); // if name is used instead of username         // ✅ admin/user dashboard

    List<MemberProfile> findByApprovedFalse();                         // ✅ admin approval list

    List<MemberProfile> findByApprovedTrue();                          // ✅ admin stats or display

    List<MemberProfile> findByCaregiverId(Long caregiverId);          // ✅ used for caregiver-member mapping
}
