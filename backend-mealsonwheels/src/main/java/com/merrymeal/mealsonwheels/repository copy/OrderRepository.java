package com.merrymeal.mealsonwheels_backend.repository;

import com.merrymeal.mealsonwheels_backend.model.Order;
import com.merrymeal.mealsonwheels_backend.model.Rider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByRider(Rider rider);

    List<Order> findByStatus(String status);

    List<Order> findByRiderAndStatusNot(Rider rider, String status); // For pending orders

    List<Order> findByRiderAndStatus(Rider rider, String status); // For delivered orders
}
