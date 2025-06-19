package com.merrymeal.mealsonwheels_backend.service.mealOrderService;

import com.merrymeal.mealsonwheels_backend.util.DistanceUtil;
import org.springframework.stereotype.Service;

@Service
public class DeliveryRangeService {

    private static final double MAX_DELIVERY_DISTANCE_KM = 10.0;

    public boolean isWithinDeliveryRange(double memberLat, double memberLon, double partnerLat, double partnerLon) {
        double distance = DistanceUtil.calculateDistanceKm(memberLat, memberLon, partnerLat, partnerLon);
        return distance <= MAX_DELIVERY_DISTANCE_KM;
    }

    public double getDistanceInKm(double memberLat, double memberLon, double partnerLat, double partnerLon) {
        return DistanceUtil.calculateDistanceKm(memberLat, memberLon, partnerLat, partnerLon);
    }
}
