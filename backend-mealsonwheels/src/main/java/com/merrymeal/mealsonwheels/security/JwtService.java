package com.merrymeal.mealsonwheels.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds


    public String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String extractUsername(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
    }

    public boolean isTokenValid(String token, String userEmail) {
        try {
            var verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            var decodedJWT = verifier.verify(token);
            String extractedEmail = decodedJWT.getSubject();
            Date expiresAt = decodedJWT.getExpiresAt();
            return extractedEmail.equals(userEmail) && expiresAt.after(new Date());
        } catch (Exception e) {
            return false; // if token is invalid or expired
        }
    }
}
