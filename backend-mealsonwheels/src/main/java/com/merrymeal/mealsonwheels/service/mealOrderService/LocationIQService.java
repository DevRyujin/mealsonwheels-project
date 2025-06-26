package com.merrymeal.mealsonwheels.service.mealOrder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class LocationIQService {

    private static final String API_KEY = "pk.80cf09e03cb70cea56965ea56862b570";

    public double[] getCoordinatesFromAddress(String address) throws IOException {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String urlString = "https://us1.locationiq.com/v1/search.php?key=" + API_KEY +
                "&q=" + encodedAddress + "&format=json";

        URI uri = URI.create(urlString);
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestMethod("GET");

        try (InputStream is = conn.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonArray = mapper.readTree(is);
            if (!jsonArray.isArray() || jsonArray.size() == 0) {
                throw new IOException("No results found for the address");
            }
            JsonNode location = jsonArray.get(0);
            double lat = location.get("lat").asDouble();
            double lon = location.get("lon").asDouble();
            return new double[] { lat, lon };
        }
    }
}
