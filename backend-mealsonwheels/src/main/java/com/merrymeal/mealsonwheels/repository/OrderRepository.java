package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByRider(RiderProfile rider);

    List<Order> findByStatus(TaskStatus status);

    List<Order> findByRiderAndStatusNot(RiderProfile rider, TaskStatus status);

    List<Order> findByRiderAndStatus(RiderProfile rider, TaskStatus status);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderMeals WHERE o.rider = :rider")
    List<Order> findByRiderWithMeals(@Param("rider") RiderProfile rider);

    Optional<Order> findTopByMemberOrderByCreatedAtDesc(MemberProfile member);


    List<Order> findByMember(MemberProfile member);
    List<Order> findByCaregiver(CaregiverProfile caregiver);
}

