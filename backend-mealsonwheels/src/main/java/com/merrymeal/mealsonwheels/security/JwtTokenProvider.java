package com.merrymeal.mealsonwheels.security;

import com.merrymeal.mealsonwheels.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;
    private Key secretKey;

    public JwtTokenProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @PostConstruct
    public void init() {
        // Ensure the secret is not null
        if (jwtConfig.getJwtSecret() == null) {
            throw new IllegalStateException("JWT Secret key is not configured properly");
        }
        this.secretKey = Keys.hmacShaKeyFor(jwtConfig.getJwtSecret().getBytes());
    }

    public String generateToken(CustomUserDetails userDetails, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getJwtExpirationInMs());

        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth != null ? auth.getAuthority() : "ROLE_UNKNOWN")
                .orElse("ROLE_UNKNOWN");

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("userId", userId)
                .claim("role", role)
                .claim("authorities", userDetails.getAuthorities().stream()
                        .map(auth -> auth.getAuthority())
                        .toList())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }




    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Logging can be added here for invalid tokens
            return false;
        }
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
