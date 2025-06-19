package com.merrymeal.mealsonwheels_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.merrymeal.mealsonwheels_backend.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    Member findByUsername(String username);
    List<Member> findByApprovedFalse();
    List<Member> findByApprovedTrue();
    List<Member> findByCaregiverId(Long caregiverId);
}
