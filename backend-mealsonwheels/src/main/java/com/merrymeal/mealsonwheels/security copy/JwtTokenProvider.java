package com.merrymeal.mealsonwheels_backend.security;

import com.merrymeal.mealsonwheels_backend.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final String ROLES_CLAIM = "roles";
    private static final String USER_ID_CLAIM = "userId";

    private final JwtConfig jwtConfig;
    private final Key key;

    public JwtTokenProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.key = Keys.hmacShaKeyFor(jwtConfig.getJwtSecret().getBytes());
    }

    // 🔐 Generate token with roles and userId
    public String generateToken(UserDetails userDetails, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getJwtExpirationInMs());

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Get approval status
        boolean approved = false;
        if (userDetails instanceof CustomUserDetails customUserDetails) {
            approved = customUserDetails.getUser().isApproved();
        }

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(Map.of(
                        ROLES_CLAIM, roles,
                        USER_ID_CLAIM, userId,
                        "approved", approved // ✅ Add this line
                ))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 📥 Extract username (email)
    public String getUsernameFromJWT(String token) {
        return parseClaims(token).getSubject();
    }

    // 📥 Extract roles
    public String getRolesFromJWT(String token) {
        return parseClaims(token).get(ROLES_CLAIM, String.class);
    }

    // 📥 Extract userId
    public Long getUserIdFromJWT(String token) {
        return parseClaims(token).get(USER_ID_CLAIM, Long.class);
    }

    // ✅ Validates token with logging
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("⚠️ JWT expired: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.out.println("⚠️ Unsupported JWT: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("⚠️ Malformed JWT: " + ex.getMessage());
        } catch (io.jsonwebtoken.security.SignatureException ex) {
            System.out.println("⚠️ Invalid signature: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("⚠️ Illegal token: " + ex.getMessage());
        }
        return false;
    }

    // 📦 Common claims parser
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
