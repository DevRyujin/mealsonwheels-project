package com.merrymeal.mealsonwheels.service.mealOrder;

import com.merrymeal.mealsonwheels.util.DistanceUtil;
import org.springframework.stereotype.Service;

@Service
public class DeliveryRangeService {

    private static final double MAX_DELIVERY_DISTANCE_KM = 10.0;

    // ✅ Check if the delivery is within allowed range
    public boolean isWithinDeliveryRange(double memberLat, double memberLon, double partnerLat, double partnerLon) {
        double distance = DistanceUtil.calculateDistanceKm(memberLat, memberLon, partnerLat, partnerLon);
        return distance <= MAX_DELIVERY_DISTANCE_KM;
    }

    // ✅ Get the distance in kilometers
    public double getDistanceInKm(double memberLat, double memberLon, double partnerLat, double partnerLon) {
        return DistanceUtil.calculateDistanceKm(memberLat, memberLon, partnerLat, partnerLon);
    }
}
