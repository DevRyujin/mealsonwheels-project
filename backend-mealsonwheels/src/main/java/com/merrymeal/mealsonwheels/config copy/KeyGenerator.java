package com.merrymeal.mealsonwheels_backend.config; // Changed package to match config folder

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;

public class KeyGenerator {
    public static void main(String[] args) {
        // Generate a 256-bit key suitable for HS256
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated JWT Secret Key (for HS256):");
        System.out.println(base64Key);
    }
}