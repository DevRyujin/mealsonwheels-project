package com.merrymeal.mealsonwheels.service.adminService;

import com.merrymeal.mealsonwheels.dto.order.MemberOrderDTO;
import com.merrymeal.mealsonwheels.model.Order;
import com.merrymeal.mealsonwheels.model.RiderProfile;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.OrderRepository;
import com.merrymeal.mealsonwheels.repository.RiderProfileRepository;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageDeliveryServiceImpl implements ManageDeliveryService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    private RiderProfileRepository riderProfileRepository;
    // include mappers or helper services if needed

    @Override
    public List<MemberOrderDTO> getMembersWithOrders() {
        // TODO: implement logic to return list of MemberOrderDTO with order summaries
        return null;
    }

    @Override
    public void assignRider(Long orderId, Long riderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        RiderProfile rider = riderProfileRepository.findById(riderId)
                .orElseThrow(() -> new RuntimeException("Rider not found"));

        order.setRider(rider);
        orderRepository.save(order);
    }


}
