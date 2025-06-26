package com.merrymeal.mealsonwheels.test;

import com.merrymeal.mealsonwheels.service.mealOrderService.LocationIQService;

public class LocationIQTestRunner {

    public static void main(String[] args) {
        LocationIQService locationIQService = new LocationIQService();

        String address = "Jollibee, Santa Maria - Tungkong Mangga Road, Tower Grottoville Subdivision, Gaya-gaya, San Jose del Monte, Bulacan, Central Luzon, 3023, Philippines";
        try {
            double[] coords = locationIQService.getCoordinatesFromAddress(address);
            System.out.println("Latitude: " + coords[0] + ", Longitude: " + coords[1]);
        } catch (Exception e) {
            System.err.println("Failed to get coordinates: " + e.getMessage());
        }
    }
}
