package com.merrymeal.mealsonwheels.repository;

import com.merrymeal.mealsonwheels.model.Order;
import com.merrymeal.mealsonwheels.model.RiderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByRider(RiderProfile rider); // Assuming Order has a field of type RiderProfile

    List<Order> findByStatus(String status);

    List<Order> findByRiderAndStatusNot(RiderProfile rider, String status);

    List<Order> findByRiderAndStatus(RiderProfile rider, String status);
}
